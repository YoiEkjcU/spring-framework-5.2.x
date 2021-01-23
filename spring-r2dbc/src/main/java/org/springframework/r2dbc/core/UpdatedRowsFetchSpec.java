package org.springframework.r2dbc.core;

import reactor.core.publisher.Mono;

/**
 * Contract for fetching the number of affected rows.
 *
 * @author Mark Paluch
 * @since 5.3
 */
public interface UpdatedRowsFetchSpec {

	/**
	 * Get the number of updated rows.
	 *
	 * @return a mono emitting the number of updated rows
	 */
	Mono<Integer> rowsUpdated();

}
