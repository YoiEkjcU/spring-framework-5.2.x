package org.springframework.http.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

/**
 * {@link ClientHttpRequest} implementation that uses standard JDK facilities to
 * execute buffered requests. Created via the {@link SimpleClientHttpRequestFactory}.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @see SimpleClientHttpRequestFactory#createRequest(java.net.URI, HttpMethod)
 * @since 3.0
 */
final class SimpleBufferingClientHttpRequest extends AbstractBufferingClientHttpRequest {

	private final HttpURLConnection connection;

	private final boolean outputStreaming;


	SimpleBufferingClientHttpRequest(HttpURLConnection connection, boolean outputStreaming) {
		this.connection = connection;
		this.outputStreaming = outputStreaming;
	}


	@Override
	public String getMethodValue() {
		return this.connection.getRequestMethod();
	}

	@Override
	public URI getURI() {
		try {
			return this.connection.getURL().toURI();
		} catch (URISyntaxException ex) {
			throw new IllegalStateException("Could not get HttpURLConnection URI: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected ClientHttpResponse executeInternal(HttpHeaders headers, byte[] bufferedOutput) throws IOException {
		addHeaders(this.connection, headers);
		// JDK <1.8 doesn't support getOutputStream with HTTP DELETE
		if (getMethod() == HttpMethod.DELETE && bufferedOutput.length == 0) {
			this.connection.setDoOutput(false);
		}
		if (this.connection.getDoOutput() && this.outputStreaming) {
			this.connection.setFixedLengthStreamingMode(bufferedOutput.length);
		}
		this.connection.connect();
		if (this.connection.getDoOutput()) {
			FileCopyUtils.copy(bufferedOutput, this.connection.getOutputStream());
		} else {
			// Immediately trigger the request in a no-output scenario as well
			this.connection.getResponseCode();
		}
		return new SimpleClientHttpResponse(this.connection);
	}


	/**
	 * Add the given headers to the given HTTP connection.
	 *
	 * @param connection the connection to add the headers to
	 * @param headers    the headers to add
	 */
	static void addHeaders(HttpURLConnection connection, HttpHeaders headers) {
		String method = connection.getRequestMethod();
		if (method.equals("PUT") || method.equals("DELETE")) {
			if (!StringUtils.hasText(headers.getFirst(HttpHeaders.ACCEPT))) {
				// Avoid "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"
				// from HttpUrlConnection which prevents JSON error response details.
				headers.set(HttpHeaders.ACCEPT, "*/*");
			}
		}
		headers.forEach((headerName, headerValues) -> {
			if (HttpHeaders.COOKIE.equalsIgnoreCase(headerName)) {  // RFC 6265
				String headerValue = StringUtils.collectionToDelimitedString(headerValues, "; ");
				connection.setRequestProperty(headerName, headerValue);
			} else {
				for (String headerValue : headerValues) {
					String actualHeaderValue = headerValue != null ? headerValue : "";
					connection.addRequestProperty(headerName, actualHeaderValue);
				}
			}
		});
	}

}
