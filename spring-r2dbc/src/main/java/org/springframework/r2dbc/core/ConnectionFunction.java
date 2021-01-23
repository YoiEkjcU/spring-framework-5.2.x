package org.springframework.r2dbc.core;

import java.util.function.Function;

import io.r2dbc.spi.Connection;

/**
 * Union type combining {@link Function} and {@link SqlProvider} to expose the SQL that is
 * related to the underlying action.
 *
 * @param <R> the type of the result of the function.
 * @author Mark Paluch
 * @since 5.3
 */
class ConnectionFunction<R> implements Function<Connection, R>, SqlProvider {

	private final String sql;

	private final Function<Connection, R> function;


	ConnectionFunction(String sql, Function<Connection, R> function) {
		this.sql = sql;
		this.function = function;
	}


	@Override
	public R apply(Connection t) {
		return this.function.apply(t);
	}

	@Override
	public String getSql() {
		return this.sql;
	}
}
