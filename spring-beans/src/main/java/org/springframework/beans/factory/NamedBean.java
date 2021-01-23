package org.springframework.beans.factory;

/**
 * Counterpart of {@link BeanNameAware}. Returns the bean name of an object.
 *
 * <p>This interface can be introduced to avoid a brittle dependence on
 * bean name in objects used with Spring IoC and Spring AOP.
 *
 * @author Rod Johnson
 * @see BeanNameAware
 * @since 2.0
 */
public interface NamedBean {

	/**
	 * Return the name of this bean in a Spring bean factory, if known.
	 */
	String getBeanName();

}
