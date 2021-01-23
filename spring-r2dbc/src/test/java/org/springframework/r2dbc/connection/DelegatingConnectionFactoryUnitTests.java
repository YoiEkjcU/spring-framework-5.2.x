package org.springframework.r2dbc.connection;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.when;

/**
 * Unit tests for {@link DelegatingConnectionFactory}.
 *
 * @author Mark Paluch
 */
class DelegatingConnectionFactoryUnitTests {

	ConnectionFactory delegate = mock(ConnectionFactory.class);

	Connection connectionMock = mock(Connection.class);

	DelegatingConnectionFactory connectionFactory = new ExampleConnectionFactory(delegate);


	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void shouldDelegateGetConnection() {
		Mono<Connection> connectionMono = Mono.just(connectionMock);
		when(delegate.create()).thenReturn((Mono) connectionMono);

		assertThat(connectionFactory.create()).isSameAs(connectionMono);
	}

	@Test
	void shouldDelegateUnwrapWithoutImplementing() {
		assertThat(connectionFactory.unwrap()).isSameAs(delegate);
	}

	static class ExampleConnectionFactory extends DelegatingConnectionFactory {

		ExampleConnectionFactory(ConnectionFactory targetConnectionFactory) {
			super(targetConnectionFactory);
		}
	}

}
