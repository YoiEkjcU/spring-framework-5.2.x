package org.springframework.beans.factory.config;

/**
 * Callback for customizing a given bean definition.
 * Designed for use with a lambda expression or method reference.
 *
 * @author Juergen Hoeller
 * @see org.springframework.beans.factory.support.BeanDefinitionBuilder#applyCustomizers
 * @since 5.0
 */
@FunctionalInterface
public interface BeanDefinitionCustomizer {

	/**
	 * Customize the given bean definition.
	 */
	void customize(BeanDefinition bd);

}
