package org.springframework.r2dbc.connection.init;

import org.springframework.dao.DataAccessException;
import org.springframework.lang.Nullable;

/**
 * Root of the hierarchy of data access exceptions that are related to processing of SQL scripts.
 *
 * @author Mark Paluch
 * @since 5.3
 */
@SuppressWarnings("serial")
public abstract class ScriptException extends DataAccessException {

	/**
	 * Create a new {@code ScriptException}.
	 *
	 * @param message the detail message
	 */
	public ScriptException(String message) {
		super(message);
	}

	/**
	 * Create a new {@code ScriptException}.
	 *
	 * @param message the detail message
	 * @param cause   the root cause
	 */
	public ScriptException(String message, @Nullable Throwable cause) {
		super(message, cause);
	}

}
