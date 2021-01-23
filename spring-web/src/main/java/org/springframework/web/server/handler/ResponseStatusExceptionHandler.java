package org.springframework.web.server.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

/**
 * Handle {@link ResponseStatusException} by setting the response status.
 *
 * <p>By default exception stack traces are not shown for successfully resolved
 * exceptions. Use {@link #setWarnLogCategory(String)} to enable logging with
 * stack traces.
 *
 * @author Rossen Stoyanchev
 * @author Sebastien Deleuze
 * @since 5.0
 */
public class ResponseStatusExceptionHandler implements WebExceptionHandler {

	private static final Log logger = LogFactory.getLog(ResponseStatusExceptionHandler.class);


	@Nullable
	private Log warnLogger;


	/**
	 * Set the log category for warn logging.
	 * <p>Default is no warn logging. Specify this setting to activate warn
	 * logging into a specific category.
	 *
	 * @see org.apache.commons.logging.LogFactory#getLog(String)
	 * @see java.util.logging.Logger#getLogger(String)
	 * @since 5.1
	 */
	public void setWarnLogCategory(String loggerName) {
		this.warnLogger = LogFactory.getLog(loggerName);
	}


	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		if (!updateResponse(exchange.getResponse(), ex)) {
			return Mono.error(ex);
		}

		// Mirrors AbstractHandlerExceptionResolver in spring-webmvc...
		String logPrefix = exchange.getLogPrefix();
		if (this.warnLogger != null && this.warnLogger.isWarnEnabled()) {
			this.warnLogger.warn(logPrefix + formatError(ex, exchange.getRequest()), ex);
		} else if (logger.isDebugEnabled()) {
			logger.debug(logPrefix + formatError(ex, exchange.getRequest()));
		}

		return exchange.getResponse().setComplete();
	}


	private String formatError(Throwable ex, ServerHttpRequest request) {
		String reason = ex.getClass().getSimpleName() + ": " + ex.getMessage();
		String path = request.getURI().getRawPath();
		return "Resolved [" + reason + "] for HTTP " + request.getMethod() + " " + path;
	}

	private boolean updateResponse(ServerHttpResponse response, Throwable ex) {
		boolean result = false;
		HttpStatus httpStatus = determineStatus(ex);
		int code = (httpStatus != null ? httpStatus.value() : determineRawStatusCode(ex));
		if (code != -1) {
			if (response.setRawStatusCode(code)) {
				if (ex instanceof ResponseStatusException) {
					((ResponseStatusException) ex).getResponseHeaders()
							.forEach((name, values) ->
									values.forEach(value -> response.getHeaders().add(name, value)));
				}
				result = true;
			}
		} else {
			Throwable cause = ex.getCause();
			if (cause != null) {
				result = updateResponse(response, cause);
			}
		}
		return result;
	}

	/**
	 * Determine the HTTP status for the given exception.
	 * <p>As of 5.3 this method always returns {@code null} in which case
	 * {@link #determineRawStatusCode(Throwable)} is used instead.
	 *
	 * @param ex the exception to check
	 * @return the associated HTTP status, if any
	 * @deprecated as of 5.3 in favor of {@link #determineRawStatusCode(Throwable)}.
	 */
	@Nullable
	@Deprecated
	protected HttpStatus determineStatus(Throwable ex) {
		return null;
	}

	/**
	 * Determine the raw status code for the given exception.
	 *
	 * @param ex the exception to check
	 * @return the associated HTTP status code, or -1 if it can't be derived.
	 * @since 5.3
	 */
	protected int determineRawStatusCode(Throwable ex) {
		if (ex instanceof ResponseStatusException) {
			return ((ResponseStatusException) ex).getRawStatusCode();
		}
		return -1;
	}

}
