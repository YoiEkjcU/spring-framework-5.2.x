package org.springframework.web.servlet.function;

/**
 * Represents a function that handles a {@linkplain ServerRequest request}.
 *
 * @param <T> the type of the response of the function
 * @author Arjen Poutsma
 * @see RouterFunction
 * @since 5.2
 */
@FunctionalInterface
public interface HandlerFunction<T extends ServerResponse> {

	/**
	 * Handle the given request.
	 *
	 * @param request the request to handle
	 * @return the response
	 */
	T handle(ServerRequest request) throws Exception;

}
