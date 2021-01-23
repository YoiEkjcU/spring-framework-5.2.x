package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanCreationException;

/**
 * A subclass of {@link BeanCreationException} which indicates that the target scope
 * is not active, e.g. in case of request or session scope.
 *
 * @author Juergen Hoeller
 * @see org.springframework.beans.factory.BeanFactory#getBean
 * @see org.springframework.beans.factory.config.Scope
 * @see AbstractBeanDefinition#setScope
 * @since 5.3
 */
@SuppressWarnings("serial")
public class ScopeNotActiveException extends BeanCreationException {

	/**
	 * Create a new ScopeNotActiveException.
	 *
	 * @param beanName  the name of the bean requested
	 * @param scopeName the name of the target scope
	 * @param cause     the root cause, typically from {@link org.springframework.beans.factory.config.Scope#get}
	 */
	public ScopeNotActiveException(String beanName, String scopeName, IllegalStateException cause) {
		super(beanName, "Scope '" + scopeName + "' is not active for the current thread; consider " +
				"defining a scoped proxy for this bean if you intend to refer to it from a singleton", cause);
	}

}
