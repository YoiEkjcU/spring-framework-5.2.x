package org.springframework.web.client;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

/**
 * Exception thrown when an unknown (or custom) HTTP status code is received.
 *
 * @author Rossen Stoyanchev
 * @since 3.2
 */
public class UnknownHttpStatusCodeException extends RestClientResponseException {

	private static final long serialVersionUID = 7103980251635005491L;


	/**
	 * Construct a new instance of {@code HttpStatusCodeException} based on an
	 * {@link HttpStatus}, status text, and response body content.
	 *
	 * @param rawStatusCode   the raw status code value
	 * @param statusText      the status text
	 * @param responseHeaders the response headers (may be {@code null})
	 * @param responseBody    the response body content (may be {@code null})
	 * @param responseCharset the response body charset (may be {@code null})
	 */
	public UnknownHttpStatusCodeException(int rawStatusCode, String statusText, @Nullable HttpHeaders responseHeaders,
										  @Nullable byte[] responseBody, @Nullable Charset responseCharset) {

		this("Unknown status code [" + rawStatusCode + "]" + " " + statusText,
				rawStatusCode, statusText, responseHeaders, responseBody, responseCharset);
	}

	/**
	 * Construct a new instance of {@code HttpStatusCodeException} based on an
	 * {@link HttpStatus}, status text, and response body content.
	 *
	 * @param rawStatusCode   the raw status code value
	 * @param statusText      the status text
	 * @param responseHeaders the response headers (may be {@code null})
	 * @param responseBody    the response body content (may be {@code null})
	 * @param responseCharset the response body charset (may be {@code null})
	 * @since 5.2.2
	 */
	public UnknownHttpStatusCodeException(String message, int rawStatusCode, String statusText,
										  @Nullable HttpHeaders responseHeaders, @Nullable byte[] responseBody, @Nullable Charset responseCharset) {

		super(message, rawStatusCode, statusText, responseHeaders, responseBody, responseCharset);
	}
}
