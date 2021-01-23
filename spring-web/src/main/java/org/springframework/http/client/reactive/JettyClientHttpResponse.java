package org.springframework.http.client.reactive;

import java.net.HttpCookie;
import java.util.List;

import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * {@link ClientHttpResponse} implementation for the Jetty ReactiveStreams HTTP client.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://github.com/jetty-project/jetty-reactive-httpclient">
 * Jetty ReactiveStreams HttpClient</a>
 * @since 5.1
 */
class JettyClientHttpResponse implements ClientHttpResponse {

	private final ReactiveResponse reactiveResponse;

	private final Flux<DataBuffer> content;

	private final HttpHeaders headers;


	public JettyClientHttpResponse(ReactiveResponse reactiveResponse, Publisher<DataBuffer> content) {
		this.reactiveResponse = reactiveResponse;
		this.content = Flux.from(content);

		MultiValueMap<String, String> adapter = new JettyHeadersAdapter(reactiveResponse.getHeaders());
		this.headers = HttpHeaders.readOnlyHttpHeaders(adapter);
	}


	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.valueOf(getRawStatusCode());
	}

	@Override
	public int getRawStatusCode() {
		return this.reactiveResponse.getStatus();
	}

	@Override
	public MultiValueMap<String, ResponseCookie> getCookies() {
		MultiValueMap<String, ResponseCookie> result = new LinkedMultiValueMap<>();
		List<String> cookieHeader = getHeaders().get(HttpHeaders.SET_COOKIE);
		if (cookieHeader != null) {
			cookieHeader.forEach(header -> HttpCookie.parse(header)
					.forEach(c -> result.add(c.getName(), ResponseCookie.fromClientResponse(c.getName(), c.getValue())
							.domain(c.getDomain())
							.path(c.getPath())
							.maxAge(c.getMaxAge())
							.secure(c.getSecure())
							.httpOnly(c.isHttpOnly())
							.build()))
			);
		}
		return CollectionUtils.unmodifiableMultiValueMap(result);
	}

	@Override
	public Flux<DataBuffer> getBody() {
		return this.content;
	}

	@Override
	public HttpHeaders getHeaders() {
		return this.headers;
	}

}
