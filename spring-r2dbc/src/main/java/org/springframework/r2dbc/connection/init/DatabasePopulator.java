package org.springframework.r2dbc.connection.init;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

import org.springframework.dao.DataAccessException;
import org.springframework.r2dbc.connection.ConnectionFactoryUtils;
import org.springframework.util.Assert;

/**
 * Strategy used to populate, initialize, or clean up a database.
 *
 * @author Mark Paluch
 * @see ResourceDatabasePopulator
 * @see ConnectionFactoryInitializer
 * @since 5.3
 */
@FunctionalInterface
public interface DatabasePopulator {

	/**
	 * Populate, initialize, or clean up the database using the
	 * provided R2DBC {@link Connection}.
	 *
	 * @param connection the R2DBC connection to use to populate the db;
	 *                   already configured and ready to use, must not be {@code null}
	 * @return {@link Mono} that initiates script execution and is
	 * notified upon completion
	 * @throws ScriptException in all other error cases
	 */
	Mono<Void> populate(Connection connection) throws ScriptException;

	/**
	 * Execute the given {@link DatabasePopulator} against the given {@link ConnectionFactory}.
	 *
	 * @param connectionFactory the {@link ConnectionFactory} to execute against
	 * @return {@link Mono} that initiates {@link DatabasePopulator#populate(Connection)}
	 * and is notified upon completion
	 */
	default Mono<Void> populate(ConnectionFactory connectionFactory)
			throws DataAccessException {
		Assert.notNull(connectionFactory, "ConnectionFactory must not be null");
		return Mono.usingWhen(ConnectionFactoryUtils.getConnection(connectionFactory), //
				this::populate, //
				connection -> ConnectionFactoryUtils.releaseConnection(connection, connectionFactory), //
				(connection, err) -> ConnectionFactoryUtils.releaseConnection(connection, connectionFactory),
				connection -> ConnectionFactoryUtils.releaseConnection(connection, connectionFactory))
				.onErrorMap(ex -> !(ex instanceof ScriptException),
						ex -> new UncategorizedScriptException("Failed to execute database script", ex));
	}

}
