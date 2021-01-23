package org.springframework.test.web.reactive.server;

import org.springframework.lang.Nullable;

/**
 * {@code ExchangeResult} sub-class that exposes the response body fully
 * extracted to a representation of type {@code <T>}.
 *
 * @param <T> the response body type
 * @author Rossen Stoyanchev
 * @see FluxExchangeResult
 * @since 5.0
 */
public class EntityExchangeResult<T> extends ExchangeResult {

	@Nullable
	private final T body;


	EntityExchangeResult(ExchangeResult result, @Nullable T body) {
		super(result);
		this.body = body;
	}


	/**
	 * Return the entity extracted from the response body.
	 */
	@Nullable
	public T getResponseBody() {
		return this.body;
	}

}
