package org.springframework.web.reactive.handler;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

/**
 * Common WebFlux exception handler that detects instances of
 * {@link org.springframework.web.server.ResponseStatusException}
 * (inherited from the base class) as well as exceptions annotated with
 * {@link ResponseStatus @ResponseStatus} by determining the HTTP status
 * for them and updating the status of the response accordingly.
 *
 * <p>If the response is already committed, the error remains unresolved
 * and is propagated.
 *
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @since 5.0.5
 */
public class WebFluxResponseStatusExceptionHandler extends ResponseStatusExceptionHandler {

	@Override
	protected int determineRawStatusCode(Throwable ex) {
		int status = super.determineRawStatusCode(ex);
		if (status == -1) {
			ResponseStatus ann = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
			if (ann != null) {
				status = ann.code().value();
			}
		}
		return status;
	}

}
