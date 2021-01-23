package org.springframework.beans.factory.config;

import org.springframework.beans.factory.NamedBean;
import org.springframework.util.Assert;

/**
 * A simple holder for a given bean name plus bean instance.
 *
 * @param <T> the bean type
 * @author Juergen Hoeller
 * @see AutowireCapableBeanFactory#resolveNamedBean(Class)
 * @since 4.3.3
 */
public class NamedBeanHolder<T> implements NamedBean {

	private final String beanName;

	private final T beanInstance;


	/**
	 * Create a new holder for the given bean name plus instance.
	 *
	 * @param beanName     the name of the bean
	 * @param beanInstance the corresponding bean instance
	 */
	public NamedBeanHolder(String beanName, T beanInstance) {
		Assert.notNull(beanName, "Bean name must not be null");
		this.beanName = beanName;
		this.beanInstance = beanInstance;
	}


	/**
	 * Return the name of the bean.
	 */
	@Override
	public String getBeanName() {
		return this.beanName;
	}

	/**
	 * Return the corresponding bean instance.
	 */
	public T getBeanInstance() {
		return this.beanInstance;
	}

}
