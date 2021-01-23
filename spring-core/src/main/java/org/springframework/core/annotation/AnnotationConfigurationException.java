package org.springframework.core.annotation;

import org.springframework.core.NestedRuntimeException;

/**
 * Thrown by {@link AnnotationUtils} and <em>synthesized annotations</em>
 * if an annotation is improperly configured.
 *
 * @author Sam Brannen
 * @see AnnotationUtils
 * @see SynthesizedAnnotation
 * @since 4.2
 */
@SuppressWarnings("serial")
public class AnnotationConfigurationException extends NestedRuntimeException {

	/**
	 * Construct a new {@code AnnotationConfigurationException} with the
	 * supplied message.
	 *
	 * @param message the detail message
	 */
	public AnnotationConfigurationException(String message) {
		super(message);
	}

	/**
	 * Construct a new {@code AnnotationConfigurationException} with the
	 * supplied message and cause.
	 *
	 * @param message the detail message
	 * @param cause   the root cause
	 */
	public AnnotationConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
