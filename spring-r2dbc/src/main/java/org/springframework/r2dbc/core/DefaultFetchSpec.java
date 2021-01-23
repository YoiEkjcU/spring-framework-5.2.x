package org.springframework.r2dbc.core;

import java.util.function.BiFunction;
import java.util.function.Function;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * Default {@link FetchSpec} implementation.
 *
 * @param <T> the row result type
 * @author Mark Paluch
 * @since 5.3
 */
class DefaultFetchSpec<T> implements FetchSpec<T> {

	private final ConnectionAccessor connectionAccessor;

	private final String sql;

	private final Function<Connection, Flux<Result>> resultFunction;

	private final Function<Connection, Mono<Integer>> updatedRowsFunction;

	private final BiFunction<Row, RowMetadata, T> mappingFunction;


	DefaultFetchSpec(ConnectionAccessor connectionAccessor, String sql,
					 Function<Connection, Flux<Result>> resultFunction,
					 Function<Connection, Mono<Integer>> updatedRowsFunction,
					 BiFunction<Row, RowMetadata, T> mappingFunction) {
		this.sql = sql;
		this.connectionAccessor = connectionAccessor;
		this.resultFunction = resultFunction;
		this.updatedRowsFunction = updatedRowsFunction;
		this.mappingFunction = mappingFunction;
	}


	@Override
	public Mono<T> one() {
		return all().buffer(2)
				.flatMap(list -> {

					if (list.isEmpty()) {
						return Mono.empty();
					}

					if (list.size() > 1) {
						return Mono.error(new IncorrectResultSizeDataAccessException(
								String.format("Query [%s] returned non unique result.",
										this.sql),
								1));
					}

					return Mono.just(list.get(0));
				}).next();
	}

	@Override
	public Mono<T> first() {
		return all().next();
	}

	@Override
	public Flux<T> all() {
		return this.connectionAccessor.inConnectionMany(new ConnectionFunction<>(this.sql,
				connection -> this.resultFunction.apply(connection)
						.flatMap(result -> result.map(this.mappingFunction))));
	}

	@Override
	public Mono<Integer> rowsUpdated() {
		return this.connectionAccessor.inConnection(this.updatedRowsFunction);
	}

}
