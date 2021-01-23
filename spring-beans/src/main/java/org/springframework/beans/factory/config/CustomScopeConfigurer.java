package org.springframework.beans.factory.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Simple {@link BeanFactoryPostProcessor} implementation that registers
 * custom {@link Scope Scope(s)} with the containing {@link ConfigurableBeanFactory}.
 *
 * <p>Will register all of the supplied {@link #setScopes(java.util.Map) scopes}
 * with the {@link ConfigurableListableBeanFactory} that is passed to the
 * {@link #postProcessBeanFactory(ConfigurableListableBeanFactory)} method.
 *
 * <p>This class allows for <i>declarative</i> registration of custom scopes.
 * Alternatively, consider implementing a custom {@link BeanFactoryPostProcessor}
 * that calls {@link ConfigurableBeanFactory#registerScope} programmatically.
 *
 * @author Juergen Hoeller
 * @author Rick Evans
 * @see ConfigurableBeanFactory#registerScope
 * @since 2.0
 */
public class CustomScopeConfigurer implements BeanFactoryPostProcessor, BeanClassLoaderAware, Ordered {

	@Nullable
	private Map<String, Object> scopes;

	private int order = Ordered.LOWEST_PRECEDENCE;

	@Nullable
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();


	/**
	 * Specify the custom scopes that are to be registered.
	 * <p>The keys indicate the scope names (of type String); each value
	 * is expected to be the corresponding custom {@link Scope} instance
	 * or class name.
	 */
	public void setScopes(Map<String, Object> scopes) {
		this.scopes = scopes;
	}

	/**
	 * Add the given scope to this configurer's map of scopes.
	 *
	 * @param scopeName the name of the scope
	 * @param scope     the scope implementation
	 * @since 4.1.1
	 */
	public void addScope(String scopeName, Scope scope) {
		if (this.scopes == null) {
			this.scopes = new LinkedHashMap<>(1);
		}
		this.scopes.put(scopeName, scope);
	}


	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}


	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (this.scopes != null) {
			this.scopes.forEach((scopeKey, value) -> {
				if (value instanceof Scope) {
					beanFactory.registerScope(scopeKey, (Scope) value);
				} else if (value instanceof Class) {
					Class<?> scopeClass = (Class<?>) value;
					Assert.isAssignable(Scope.class, scopeClass, "Invalid scope class");
					beanFactory.registerScope(scopeKey, (Scope) BeanUtils.instantiateClass(scopeClass));
				} else if (value instanceof String) {
					Class<?> scopeClass = ClassUtils.resolveClassName((String) value, this.beanClassLoader);
					Assert.isAssignable(Scope.class, scopeClass, "Invalid scope class");
					beanFactory.registerScope(scopeKey, (Scope) BeanUtils.instantiateClass(scopeClass));
				} else {
					throw new IllegalArgumentException("Mapped value [" + value + "] for scope key [" +
							scopeKey + "] is not an instance of required type [" + Scope.class.getName() +
							"] or a corresponding Class or String value indicating a Scope implementation");
				}
			});
		}
	}

}
