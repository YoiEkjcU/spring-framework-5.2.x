package org.springframework.http.client;

import java.io.IOException;

import org.springframework.http.HttpRequest;

/**
 * Intercepts client-side HTTP requests. Implementations of this interface can be
 * {@linkplain org.springframework.web.client.RestTemplate#setInterceptors registered}
 * with the {@link org.springframework.web.client.RestTemplate RestTemplate},
 * as to modify the outgoing {@link ClientHttpRequest} and/or the incoming
 * {@link ClientHttpResponse}.
 *
 * <p>The main entry point for interceptors is
 * {@link #intercept(HttpRequest, byte[], ClientHttpRequestExecution)}.
 *
 * @author Arjen Poutsma
 * @since 3.1
 */
@FunctionalInterface
public interface ClientHttpRequestInterceptor {

	/**
	 * Intercept the given request, and return a response. The given
	 * {@link ClientHttpRequestExecution} allows the interceptor to pass on the
	 * request and response to the next entity in the chain.
	 * <p>A typical implementation of this method would follow the following pattern:
	 * <ol>
	 * <li>Examine the {@linkplain HttpRequest request} and body</li>
	 * <li>Optionally {@linkplain org.springframework.http.client.support.HttpRequestWrapper
	 * wrap} the request to filter HTTP attributes.</li>
	 * <li>Optionally modify the body of the request.</li>
	 * <li><strong>Either</strong>
	 * <ul>
	 * <li>execute the request using
	 * {@link ClientHttpRequestExecution#execute(org.springframework.http.HttpRequest, byte[])},</li>
	 * <strong>or</strong>
	 * <li>do not execute the request to block the execution altogether.</li>
	 * </ul>
	 * <li>Optionally wrap the response to filter HTTP attributes.</li>
	 * </ol>
	 *
	 * @param request   the request, containing method, URI, and headers
	 * @param body      the body of the request
	 * @param execution the request execution
	 * @return the response
	 * @throws IOException in case of I/O errors
	 */
	ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException;

}
