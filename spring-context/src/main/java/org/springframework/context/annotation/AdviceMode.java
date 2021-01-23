package org.springframework.context.annotation;

/**
 * Enumeration used to determine whether JDK proxy-based or
 * AspectJ weaving-based advice should be applied.
 *
 * @author Chris Beams
 * @see org.springframework.scheduling.annotation.EnableAsync#mode()
 * @see org.springframework.scheduling.annotation.AsyncConfigurationSelector#selectImports
 * @see org.springframework.transaction.annotation.EnableTransactionManagement#mode()
 * @since 3.1
 */
public enum AdviceMode {

	/**
	 * JDK proxy-based advice.
	 */
	PROXY,

	/**
	 * AspectJ weaving-based advice.
	 */
	ASPECTJ

}
