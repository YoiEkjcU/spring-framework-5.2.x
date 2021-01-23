package org.springframework.web.reactive.function.server;

import reactor.core.publisher.Mono;

/**
 * Represents a function that handles a {@linkplain ServerRequest request}.
 *
 * @param <T> the type of the response of the function
 * @author Arjen Poutsma
 * @see RouterFunction
 * @since 5.0
 */
@FunctionalInterface
public interface HandlerFunction<T extends ServerResponse> {

	/**
	 * Handle the given request.
	 *
	 * @param request the request to handle
	 * @return the response
	 */
	Mono<T> handle(ServerRequest request);

}
