package org.springframework.r2dbc.connection.lookup;

import java.util.HashMap;
import java.util.Map;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link MapConnectionFactoryLookup}.
 *
 * @author Mark Paluch
 */
public class MapConnectionFactoryLookupUnitTests {

	private static final String CONNECTION_FACTORY_NAME = "connectionFactory";

	@Test
	public void getConnectionFactorysReturnsUnmodifiableMap() {
		MapConnectionFactoryLookup lookup = new MapConnectionFactoryLookup();
		Map<String, ConnectionFactory> connectionFactories = lookup.getConnectionFactories();

		assertThatThrownBy(() -> connectionFactories.put("",
				new DummyConnectionFactory())).isInstanceOf(
						UnsupportedOperationException.class);
	}

	@Test
	public void shouldLookupConnectionFactory() {
		Map<String, ConnectionFactory> connectionFactories = new HashMap<>();
		DummyConnectionFactory expectedConnectionFactory = new DummyConnectionFactory();

		connectionFactories.put(CONNECTION_FACTORY_NAME, expectedConnectionFactory);
		MapConnectionFactoryLookup lookup = new MapConnectionFactoryLookup();

		lookup.setConnectionFactories(connectionFactories);

		ConnectionFactory connectionFactory = lookup.getConnectionFactory(
				CONNECTION_FACTORY_NAME);

		assertThat(connectionFactory).isNotNull().isSameAs(expectedConnectionFactory);
	}

	@Test
	public void addingConnectionFactoryPermitsOverride() {
		Map<String, ConnectionFactory> connectionFactories = new HashMap<>();
		DummyConnectionFactory overriddenConnectionFactory = new DummyConnectionFactory();
		DummyConnectionFactory expectedConnectionFactory = new DummyConnectionFactory();
		connectionFactories.put(CONNECTION_FACTORY_NAME, overriddenConnectionFactory);

		MapConnectionFactoryLookup lookup = new MapConnectionFactoryLookup();

		lookup.setConnectionFactories(connectionFactories);
		lookup.addConnectionFactory(CONNECTION_FACTORY_NAME, expectedConnectionFactory);

		ConnectionFactory connectionFactory = lookup.getConnectionFactory(
				CONNECTION_FACTORY_NAME);

		assertThat(connectionFactory).isNotNull().isSameAs(expectedConnectionFactory);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getConnectionFactoryWhereSuppliedMapHasNonConnectionFactoryTypeUnderSpecifiedKey() {
		Map connectionFactories = new HashMap<>();
		connectionFactories.put(CONNECTION_FACTORY_NAME, new Object());
		MapConnectionFactoryLookup lookup = new MapConnectionFactoryLookup(
				connectionFactories);

		assertThatThrownBy(
				() -> lookup.getConnectionFactory(CONNECTION_FACTORY_NAME)).isInstanceOf(
						ClassCastException.class);
	}

	@Test
	public void getConnectionFactoryWhereSuppliedMapHasNoEntryForSpecifiedKey() {
		MapConnectionFactoryLookup lookup = new MapConnectionFactoryLookup();

		assertThatThrownBy(
				() -> lookup.getConnectionFactory(CONNECTION_FACTORY_NAME)).isInstanceOf(
						ConnectionFactoryLookupFailureException.class);
	}
}
