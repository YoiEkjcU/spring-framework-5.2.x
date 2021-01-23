package org.springframework.test.web.servlet.request;

import org.springframework.test.web.servlet.SmartRequestBuilder;

/**
 * An extension of {@link org.springframework.test.web.servlet.SmartRequestBuilder
 * SmartRequestBuilder} that can be configured with {@link RequestPostProcessor RequestPostProcessors}.
 *
 * @param <B> a self reference to the builder type
 * @author Rossen Stoyanchev
 * @since 4.1
 */
public interface ConfigurableSmartRequestBuilder<B extends ConfigurableSmartRequestBuilder<B>>
		extends SmartRequestBuilder {

	/**
	 * Add the given {@code RequestPostProcessor}.
	 */
	B with(RequestPostProcessor requestPostProcessor);

}
