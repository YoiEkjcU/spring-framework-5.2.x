package org.springframework.core.io.support;

import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link SpringFactoriesLoader}.
 *
 * @author Arjen Poutsma
 * @author Phillip Webb
 * @author Sam Brannen
 */
class SpringFactoriesLoaderTests {

	@BeforeAll
	static void clearCache() {
		SpringFactoriesLoader.cache.clear();
		assertThat(SpringFactoriesLoader.cache).isEmpty();
	}

	@AfterAll
	static void checkCache() {
		assertThat(SpringFactoriesLoader.cache).hasSize(1);
	}

	@Test
	void loadFactoryNames() {
		List<String> factoryNames = SpringFactoriesLoader.loadFactoryNames(DummyFactory.class, null);
		assertThat(factoryNames).containsExactlyInAnyOrder(MyDummyFactory1.class.getName(), MyDummyFactory2.class.getName());
	}

	@Test
	void loadFactoriesWithNoRegisteredImplementations() {
		List<Integer> factories = SpringFactoriesLoader.loadFactories(Integer.class, null);
		assertThat(factories).isEmpty();
	}

	@Test
	void loadFactoriesInCorrectOrderWithDuplicateRegistrationsPresent() {
		List<DummyFactory> factories = SpringFactoriesLoader.loadFactories(DummyFactory.class, null);
		assertThat(factories).hasSize(2);
		assertThat(factories.get(0)).isInstanceOf(MyDummyFactory1.class);
		assertThat(factories.get(1)).isInstanceOf(MyDummyFactory2.class);
	}

	@Test
	void loadPackagePrivateFactory() {
		List<DummyPackagePrivateFactory> factories =
				SpringFactoriesLoader.loadFactories(DummyPackagePrivateFactory.class, null);
		assertThat(factories).hasSize(1);
		assertThat(Modifier.isPublic(factories.get(0).getClass().getModifiers())).isFalse();
	}

	@Test
	void attemptToLoadFactoryOfIncompatibleType() {
		assertThatIllegalArgumentException()
			.isThrownBy(() -> SpringFactoriesLoader.loadFactories(String.class, null))
			.withMessageContaining("Unable to instantiate factory class "
					+ "[org.springframework.core.io.support.MyDummyFactory1] for factory type [java.lang.String]");
	}

}
