package org.springframework.r2dbc.connection.init;

import java.util.concurrent.atomic.AtomicBoolean;

import io.r2dbc.spi.test.MockConnection;
import io.r2dbc.spi.test.MockConnectionFactory;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.when;

/**
 * Unit tests for {@link ConnectionFactoryInitializer}.
 *
 * @author Mark Paluch
 */
public class ConnectionFactoryInitializerUnitTests {

	AtomicBoolean called = new AtomicBoolean();

	DatabasePopulator populator = mock(DatabasePopulator.class);

	MockConnection connection = MockConnection.builder().build();

	MockConnectionFactory connectionFactory = MockConnectionFactory.builder().connection(
			connection).build();


	@Test
	public void shouldInitializeConnectionFactory() {
		when(populator.populate(connectionFactory)).thenReturn(
				Mono.<Void> empty().doOnSubscribe(subscription -> called.set(true)));

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(populator);

		initializer.afterPropertiesSet();

		assertThat(called).isTrue();
	}

	@Test
	public void shouldCleanConnectionFactory() {
		when(populator.populate(connectionFactory)).thenReturn(
				Mono.<Void> empty().doOnSubscribe(subscription -> called.set(true)));

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabaseCleaner(populator);

		initializer.afterPropertiesSet();
		initializer.destroy();

		assertThat(called).isTrue();
	}

}
