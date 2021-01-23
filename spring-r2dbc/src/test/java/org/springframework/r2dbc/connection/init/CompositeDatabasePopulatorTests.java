package org.springframework.r2dbc.connection.init;

import java.util.LinkedHashSet;
import java.util.Set;

import io.r2dbc.spi.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;

/**
 * Unit tests for {@link CompositeDatabasePopulator}.
 *
 * @author Mark Paluch
 */
public class CompositeDatabasePopulatorTests {

	Connection mockedConnection = mock(Connection.class);

	DatabasePopulator mockedDatabasePopulator1 = mock(DatabasePopulator.class);

	DatabasePopulator mockedDatabasePopulator2 = mock(DatabasePopulator.class);


	@BeforeEach
	public void before() {
		when(mockedDatabasePopulator1.populate(mockedConnection)).thenReturn(
				Mono.empty());
		when(mockedDatabasePopulator2.populate(mockedConnection)).thenReturn(
				Mono.empty());
	}

	@Test
	public void addPopulators() {
		CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
		populator.addPopulators(mockedDatabasePopulator1, mockedDatabasePopulator2);

		populator.populate(mockedConnection).as(StepVerifier::create).verifyComplete();

		verify(mockedDatabasePopulator1, times(1)).populate(mockedConnection);
		verify(mockedDatabasePopulator2, times(1)).populate(mockedConnection);
	}

	@Test
	public void setPopulatorsWithMultiple() {
		CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
		populator.setPopulators(mockedDatabasePopulator1, mockedDatabasePopulator2); // multiple

		populator.populate(mockedConnection).as(StepVerifier::create).verifyComplete();

		verify(mockedDatabasePopulator1, times(1)).populate(mockedConnection);
		verify(mockedDatabasePopulator2, times(1)).populate(mockedConnection);
	}

	@Test
	public void setPopulatorsForOverride() {
		CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
		populator.setPopulators(mockedDatabasePopulator1);
		populator.setPopulators(mockedDatabasePopulator2); // override

		populator.populate(mockedConnection).as(StepVerifier::create).verifyComplete();

		verify(mockedDatabasePopulator1, times(0)).populate(mockedConnection);
		verify(mockedDatabasePopulator2, times(1)).populate(mockedConnection);
	}

	@Test
	public void constructWithVarargs() {
		CompositeDatabasePopulator populator = new CompositeDatabasePopulator(
				mockedDatabasePopulator1, mockedDatabasePopulator2);

		populator.populate(mockedConnection).as(StepVerifier::create).verifyComplete();

		verify(mockedDatabasePopulator1, times(1)).populate(mockedConnection);
		verify(mockedDatabasePopulator2, times(1)).populate(mockedConnection);
	}

	@Test
	public void constructWithCollection() {
		Set<DatabasePopulator> populators = new LinkedHashSet<>();
		populators.add(mockedDatabasePopulator1);
		populators.add(mockedDatabasePopulator2);

		CompositeDatabasePopulator populator = new CompositeDatabasePopulator(populators);
		populator.populate(mockedConnection).as(StepVerifier::create).verifyComplete();

		verify(mockedDatabasePopulator1, times(1)).populate(mockedConnection);
		verify(mockedDatabasePopulator2, times(1)).populate(mockedConnection);
	}

}
