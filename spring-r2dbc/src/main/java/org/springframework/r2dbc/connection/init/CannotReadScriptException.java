package org.springframework.r2dbc.connection.init;

import org.springframework.core.io.support.EncodedResource;

/**
 * Thrown by {@link ScriptUtils} if an SQL script cannot be read.
 *
 * @author Mark Paluch
 * @since 5.3
 */
@SuppressWarnings("serial")
public class CannotReadScriptException extends ScriptException {

	/**
	 * Create a new {@code CannotReadScriptException}.
	 *
	 * @param resource the resource that cannot be read from.
	 * @param cause    the underlying cause of the resource access failure.
	 */
	public CannotReadScriptException(EncodedResource resource, Throwable cause) {
		super("Cannot read SQL script from " + resource, cause);
	}

}
