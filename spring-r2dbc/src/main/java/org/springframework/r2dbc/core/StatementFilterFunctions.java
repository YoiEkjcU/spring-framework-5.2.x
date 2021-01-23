package org.springframework.r2dbc.core;

import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import org.reactivestreams.Publisher;

/**
 * Collection of default {@link StatementFilterFunction}s.
 *
 * @author Mark Paluch
 * @since 5.3
 */
enum StatementFilterFunctions implements StatementFilterFunction {

	EMPTY_FILTER;


	@Override
	public Publisher<? extends Result> filter(Statement statement, ExecuteFunction next) {
		return next.execute(statement);
	}

	/**
	 * Return an empty {@link StatementFilterFunction} that delegates to {@link ExecuteFunction}.
	 *
	 * @return an empty {@link StatementFilterFunction} that delegates to {@link ExecuteFunction}.
	 */
	public static StatementFilterFunction empty() {
		return EMPTY_FILTER;
	}

}
