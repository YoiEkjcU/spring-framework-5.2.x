package org.springframework.beans.factory;

/**
 * Exception thrown when a bean instance has been requested for
 * a bean definition which has been marked as abstract.
 *
 * @author Juergen Hoeller
 * @see org.springframework.beans.factory.support.AbstractBeanDefinition#setAbstract
 * @since 1.1
 */
@SuppressWarnings("serial")
public class BeanIsAbstractException extends BeanCreationException {

	/**
	 * Create a new BeanIsAbstractException.
	 *
	 * @param beanName the name of the bean requested
	 */
	public BeanIsAbstractException(String beanName) {
		super(beanName, "Bean definition is abstract");
	}

}
