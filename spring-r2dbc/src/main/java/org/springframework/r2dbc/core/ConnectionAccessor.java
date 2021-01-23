package org.springframework.r2dbc.core;

import java.util.function.Function;

import io.r2dbc.spi.Connection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.dao.DataAccessException;

/**
 * Interface declaring methods that accept callback {@link Function}
 * to operate within the scope of a {@link Connection}.
 * Callback functions operate on a provided connection and must not
 * close the connection as the connections may be pooled or be
 * subject to other kinds of resource management.
 *
 * <p> Callback functions are responsible for creating a
 * {@link org.reactivestreams.Publisher} that defines the scope of how
 * long the allocated {@link Connection} is valid. Connections are
 * released after the publisher terminates.
 *
 * @author Mark Paluch
 * @since 5.3
 */
public interface ConnectionAccessor {

	/**
	 * Execute a callback {@link Function} within a {@link Connection} scope.
	 * The function is responsible for creating a {@link Mono}. The connection
	 * is released after the {@link Mono} terminates (or the subscription
	 * is cancelled). Connection resources must not be passed outside of the
	 * {@link Function} closure, otherwise resources may get defunct.
	 *
	 * @param action the callback object that specifies the connection action
	 * @return the resulting {@link Mono}
	 */
	<T> Mono<T> inConnection(Function<Connection, Mono<T>> action) throws DataAccessException;

	/**
	 * Execute a callback {@link Function} within a {@link Connection} scope.
	 * The function is responsible for creating a {@link Flux}. The connection
	 * is released after the {@link Flux} terminates (or the subscription
	 * is cancelled). Connection resources must not be passed outside of the
	 * {@link Function} closure, otherwise resources may get defunct.
	 *
	 * @param action the callback object that specifies the connection action
	 * @return the resulting {@link Flux}
	 */
	<T> Flux<T> inConnectionMany(Function<Connection, Flux<T>> action) throws DataAccessException;

}
