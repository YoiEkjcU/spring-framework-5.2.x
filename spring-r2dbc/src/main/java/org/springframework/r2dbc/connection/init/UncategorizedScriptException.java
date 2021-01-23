package org.springframework.r2dbc.connection.init;

/**
 * Thrown when we cannot determine anything more specific than "something went wrong while
 * processing an SQL script": for example, a {@link io.r2dbc.spi.R2dbcException} from
 * R2DBC that we cannot pinpoint more precisely.
 *
 * @author Mark Paluch
 * @since 5.3
 */
@SuppressWarnings("serial")
public class UncategorizedScriptException extends ScriptException {

	/**
	 * Create a new {@code UncategorizedScriptException}.
	 *
	 * @param message detailed message
	 */
	public UncategorizedScriptException(String message) {
		super(message);
	}

	/**
	 * Create a new {@code UncategorizedScriptException}.
	 *
	 * @param message detailed message
	 * @param cause   the root cause
	 */
	public UncategorizedScriptException(String message, Throwable cause) {
		super(message, cause);
	}

}
