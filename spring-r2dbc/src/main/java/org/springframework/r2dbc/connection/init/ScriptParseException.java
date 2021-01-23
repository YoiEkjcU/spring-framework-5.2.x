package org.springframework.r2dbc.connection.init;

import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;

/**
 * Thrown by {@link ScriptUtils} if an SQL script cannot be properly parsed.
 *
 * @author Mark Paluch
 * @since 5.3
 */
@SuppressWarnings("serial")
public class ScriptParseException extends ScriptException {

	/**
	 * Create a new {@code ScriptParseException}.
	 *
	 * @param message  detailed message
	 * @param resource the resource from which the SQL script was read
	 */
	public ScriptParseException(String message, @Nullable EncodedResource resource) {
		super(buildMessage(message, resource));
	}

	/**
	 * Create a new {@code ScriptParseException}.
	 *
	 * @param message  detailed message
	 * @param resource the resource from which the SQL script was read
	 * @param cause    the underlying cause of the failure
	 */
	public ScriptParseException(String message, @Nullable EncodedResource resource, @Nullable Throwable cause) {
		super(buildMessage(message, resource), cause);
	}


	private static String buildMessage(String message, @Nullable EncodedResource resource) {
		return String.format("Failed to parse SQL script from resource [%s]: %s",
				(resource == null ? "<unknown>" : resource), message);
	}

}
