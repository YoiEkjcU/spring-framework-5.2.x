package org.springframework.dao;

/**
 * Data access exception thrown when a previously failed operation might be able
 * to succeed if the application performs some recovery steps and retries the entire
 * transaction or in the case of a distributed transaction, the transaction branch.
 * At a minimum, the recovery operation must include closing the current connection
 * and getting a new connection.
 *
 * @author Thomas Risberg
 * @see java.sql.SQLRecoverableException
 * @since 2.5
 */
@SuppressWarnings("serial")
public class RecoverableDataAccessException extends DataAccessException {

	/**
	 * Constructor for RecoverableDataAccessException.
	 *
	 * @param msg the detail message
	 */
	public RecoverableDataAccessException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for RecoverableDataAccessException.
	 *
	 * @param msg   the detail message
	 * @param cause the root cause (usually from using a underlying
	 *              data access API such as JDBC)
	 */
	public RecoverableDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
