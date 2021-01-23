package org.springframework.r2dbc.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Contract for fetching tabular results.
 *
 * @param <T> the row result type
 * @author Mark Paluch
 * @since 5.3
 */
public interface RowsFetchSpec<T> {

	/**
	 * Get exactly zero or one result.
	 *
	 * @return a mono emitting one element. {@link Mono#empty()} if no match found.
	 * Completes with {@code IncorrectResultSizeDataAccessException} if more than one match found
	 */
	Mono<T> one();

	/**
	 * Get the first or no result.
	 *
	 * @return a mono emitting the first element. {@link Mono#empty()} if no match found
	 */
	Mono<T> first();

	/**
	 * Get all matching elements.
	 *
	 * @return a flux emitting all results
	 */
	Flux<T> all();

}
