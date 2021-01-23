package org.springframework.remoting.httpinvoker;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Stephane Nicoll
 */
class HttpInvokerFactoryBeanIntegrationTests {

	@Test
	@SuppressWarnings("resource")
	void testLoadedConfigClass() {
		ApplicationContext context = new AnnotationConfigApplicationContext(InvokerAutowiringConfig.class);
		MyBean myBean = context.getBean("myBean", MyBean.class);
		assertThat(myBean.myService).isSameAs(context.getBean("myService"));
		myBean.myService.handle();
		myBean.myService.handleAsync();
	}

	@Test
	@SuppressWarnings("resource")
	void testNonLoadedConfigClass() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.registerBeanDefinition("config", new RootBeanDefinition(InvokerAutowiringConfig.class.getName()));
		context.refresh();
		MyBean myBean = context.getBean("myBean", MyBean.class);
		assertThat(myBean.myService).isSameAs(context.getBean("myService"));
		myBean.myService.handle();
		myBean.myService.handleAsync();
	}

	@Test
	@SuppressWarnings("resource")
	void withConfigurationClassWithPlainFactoryBean() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ConfigWithPlainFactoryBean.class);
		context.refresh();
		MyBean myBean = context.getBean("myBean", MyBean.class);
		assertThat(myBean.myService).isSameAs(context.getBean("myService"));
		myBean.myService.handle();
		myBean.myService.handleAsync();
	}


	interface MyService {

		void handle();

		@Async
		public void handleAsync();
	}


	@Component("myBean")
	static class MyBean {

		@Autowired
		MyService myService;
	}


	@Configuration
	@ComponentScan
	@Lazy
	static class InvokerAutowiringConfig {

		@Bean
		AsyncAnnotationBeanPostProcessor aabpp() {
			return new AsyncAnnotationBeanPostProcessor();
		}

		@Bean
		@SuppressWarnings("deprecation")
		HttpInvokerProxyFactoryBean myService() {
			HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
			factory.setServiceUrl("/svc/dummy");
			factory.setServiceInterface(MyService.class);
			factory.setHttpInvokerRequestExecutor((config, invocation) -> new RemoteInvocationResult());
			return factory;
		}

		@Bean
		FactoryBean<String> myOtherService() {
			throw new IllegalStateException("Don't ever call me");
		}
	}


	@Configuration
	static class ConfigWithPlainFactoryBean {

		@Autowired
		Environment env;

		@Bean
		MyBean myBean() {
			return new MyBean();
		}

		@Bean
		@SuppressWarnings("deprecation")
		HttpInvokerProxyFactoryBean myService() {
			String name = env.getProperty("testbean.name");
			HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
			factory.setServiceUrl("/svc/" + name);
			factory.setServiceInterface(MyService.class);
			factory.setHttpInvokerRequestExecutor((config, invocation) -> new RemoteInvocationResult());
			return factory;
		}
	}

}
