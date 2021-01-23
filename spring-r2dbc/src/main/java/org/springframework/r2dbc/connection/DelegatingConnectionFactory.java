package org.springframework.r2dbc.connection;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import io.r2dbc.spi.Wrapped;
import reactor.core.publisher.Mono;

import org.springframework.util.Assert;

/**
 * R2DBC {@link ConnectionFactory} implementation that delegates
 * all calls to a given target {@link ConnectionFactory}.
 *
 * <p>This class is meant to be subclassed, with subclasses overriding
 * only those methods (such as {@link #create()}) that should not simply
 * delegate to the target {@link ConnectionFactory}.
 *
 * @author Mark Paluch
 * @see #create
 * @since 5.3
 */
public class DelegatingConnectionFactory implements ConnectionFactory, Wrapped<ConnectionFactory> {

	private final ConnectionFactory targetConnectionFactory;


	public DelegatingConnectionFactory(ConnectionFactory targetConnectionFactory) {
		Assert.notNull(targetConnectionFactory, "ConnectionFactory must not be null");
		this.targetConnectionFactory = targetConnectionFactory;
	}


	@Override
	public Mono<? extends Connection> create() {
		return Mono.from(this.targetConnectionFactory.create());
	}

	public ConnectionFactory getTargetConnectionFactory() {
		return this.targetConnectionFactory;
	}

	@Override
	public ConnectionFactoryMetadata getMetadata() {
		return obtainTargetConnectionFactory().getMetadata();
	}

	@Override
	public ConnectionFactory unwrap() {
		return obtainTargetConnectionFactory();
	}

	/**
	 * Obtain the target {@link ConnectionFactory} for actual use (never {@code null}).
	 */
	protected ConnectionFactory obtainTargetConnectionFactory() {
		return getTargetConnectionFactory();
	}

}
