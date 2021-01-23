package org.springframework.web.context.request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ScopeNotActiveException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.testfixture.beans.CountingTestBean;
import org.springframework.beans.testfixture.beans.DerivedTestBean;
import org.springframework.beans.testfixture.beans.TestBean;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.testfixture.servlet.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR;

/**
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Sam Brannen
 * @see SessionScopeTests
 */
public class RequestScopeTests {

	private final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();


	@BeforeEach
	public void setup() {
		this.beanFactory.registerScope("request", new RequestScope());
		this.beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver());
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this.beanFactory);
		reader.loadBeanDefinitions(new ClassPathResource("requestScopeTests.xml", getClass()));
		this.beanFactory.preInstantiateSingletons();
	}

	@AfterEach
	public void resetRequestAttributes() {
		RequestContextHolder.setRequestAttributes(null);
	}


	@Test
	public void getFromScope() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContextPath("/path");
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		String name = "requestScopedObject";
		assertThat(request.getAttribute(name)).isNull();
		TestBean bean = (TestBean) this.beanFactory.getBean(name);
		assertThat(bean.getName()).isEqualTo("/path");
		assertThat(request.getAttribute(name)).isSameAs(bean);
		assertThat(this.beanFactory.getBean(name)).isSameAs(bean);
	}

	@Test
	public void destructionAtRequestCompletion() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		String name = "requestScopedDisposableObject";
		assertThat(request.getAttribute(name)).isNull();
		DerivedTestBean bean = (DerivedTestBean) this.beanFactory.getBean(name);
		assertThat(request.getAttribute(name)).isSameAs(bean);
		assertThat(this.beanFactory.getBean(name)).isSameAs(bean);

		requestAttributes.requestCompleted();
		assertThat(bean.wasDestroyed()).isTrue();
	}

	@Test
	public void getFromFactoryBeanInScope() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		String name = "requestScopedFactoryBean";
		assertThat(request.getAttribute(name)).isNull();
		TestBean bean = (TestBean) this.beanFactory.getBean(name);
		boolean condition = request.getAttribute(name) instanceof FactoryBean;
		assertThat(condition).isTrue();
		assertThat(this.beanFactory.getBean(name)).isSameAs(bean);
	}

	@Test
	public void circleLeadsToException() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		String name = "requestScopedObjectCircle1";
		assertThat(request.getAttribute(name)).isNull();
		assertThatExceptionOfType(BeanCreationException.class).isThrownBy(() ->
				this.beanFactory.getBean(name))
			.matches(ex -> ex.contains(BeanCurrentlyInCreationException.class));
	}

	@Test
	public void innerBeanInheritsContainingBeanScopeByDefault() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		String outerBeanName = "requestScopedOuterBean";
		assertThat(request.getAttribute(outerBeanName)).isNull();
		TestBean outer1 = (TestBean) this.beanFactory.getBean(outerBeanName);
		assertThat(request.getAttribute(outerBeanName)).isNotNull();
		TestBean inner1 = (TestBean) outer1.getSpouse();
		assertThat(this.beanFactory.getBean(outerBeanName)).isSameAs(outer1);
		requestAttributes.requestCompleted();
		assertThat(outer1.wasDestroyed()).isTrue();
		assertThat(inner1.wasDestroyed()).isTrue();
		request = new MockHttpServletRequest();
		requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);
		TestBean outer2 = (TestBean) this.beanFactory.getBean(outerBeanName);
		assertThat(outer2).isNotSameAs(outer1);
		assertThat(outer2.getSpouse()).isNotSameAs(inner1);
	}

	@Test
	public void requestScopedInnerBeanDestroyedWhileContainedBySingleton() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		String outerBeanName = "singletonOuterBean";
		TestBean outer1 = (TestBean) this.beanFactory.getBean(outerBeanName);
		assertThat(request.getAttribute(outerBeanName)).isNull();
		TestBean inner1 = (TestBean) outer1.getSpouse();
		TestBean outer2 = (TestBean) this.beanFactory.getBean(outerBeanName);
		assertThat(outer2).isSameAs(outer1);
		assertThat(outer2.getSpouse()).isSameAs(inner1);
		requestAttributes.requestCompleted();
		assertThat(inner1.wasDestroyed()).isTrue();
		assertThat(outer1.wasDestroyed()).isFalse();
	}

	@Test
	public void scopeNotAvailable() {
		assertThatExceptionOfType(ScopeNotActiveException.class).isThrownBy(
				() -> this.beanFactory.getBean(CountingTestBean.class));

		ObjectProvider<CountingTestBean> beanProvider = this.beanFactory.getBeanProvider(CountingTestBean.class);
		assertThatExceptionOfType(ScopeNotActiveException.class).isThrownBy(beanProvider::getObject);
		assertThat(beanProvider.getIfAvailable()).isNull();
		assertThat(beanProvider.getIfUnique()).isNull();

		ObjectProvider<CountingTestBean> provider =
				((ProviderBean) this.beanFactory.createBean(ProviderBean.class, AUTOWIRE_CONSTRUCTOR, false)).provider;
		assertThatExceptionOfType(ScopeNotActiveException.class).isThrownBy(provider::getObject);
		assertThat(provider.getIfAvailable()).isNull();
		assertThat(provider.getIfUnique()).isNull();
	}


	public static class ProviderBean {

		public ObjectProvider<CountingTestBean> provider;

		public ProviderBean(ObjectProvider<CountingTestBean> provider) {
			this.provider = provider;
		}
	}

}
