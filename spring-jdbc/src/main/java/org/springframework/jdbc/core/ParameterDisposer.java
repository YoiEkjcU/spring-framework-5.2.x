package org.springframework.jdbc.core;

/**
 * Interface to be implemented by objects that can close resources
 * allocated by parameters like {@code SqlLobValue} objects.
 *
 * <p>Typically implemented by {@code PreparedStatementCreators} and
 * {@code PreparedStatementSetters} that support {@link DisposableSqlTypeValue}
 * objects (e.g. {@code SqlLobValue}) as parameters.
 *
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @see PreparedStatementCreator
 * @see PreparedStatementSetter
 * @see DisposableSqlTypeValue
 * @see org.springframework.jdbc.core.support.SqlLobValue
 * @since 1.1
 */
public interface ParameterDisposer {

	/**
	 * Close the resources allocated by parameters that the implementing
	 * object holds, for example in case of a DisposableSqlTypeValue
	 * (like an SqlLobValue).
	 *
	 * @see DisposableSqlTypeValue#cleanup()
	 * @see org.springframework.jdbc.core.support.SqlLobValue#cleanup()
	 */
	void cleanupParameters();

}
