package org.springframework.r2dbc;

import io.r2dbc.spi.R2dbcException;

import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.lang.Nullable;

/**
 * Exception thrown when we can't classify a {@link R2dbcException} into
 * one of our generic data access exceptions.
 *
 * @author Mark Paluch
 * @since 5.3
 */
@SuppressWarnings("serial")
public class UncategorizedR2dbcException extends UncategorizedDataAccessException {

	/**
	 * SQL that led to the problem.
	 */
	@Nullable
	private final String sql;


	/**
	 * Constructor for {@code UncategorizedSQLException}.
	 *
	 * @param msg the detail message
	 * @param sql the offending SQL statement
	 * @param ex  the exception thrown by underlying data access API
	 */
	public UncategorizedR2dbcException(String msg, @Nullable String sql, R2dbcException ex) {
		super(msg, ex);
		this.sql = sql;
	}


	/**
	 * Return the wrapped {@link R2dbcException}.
	 */
	public R2dbcException getR2dbcException() {
		return (R2dbcException) getCause();
	}

	/**
	 * Return the SQL that led to the problem (if known).
	 */
	@Nullable
	public String getSql() {
		return this.sql;
	}

}
