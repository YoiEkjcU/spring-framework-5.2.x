package org.springframework.r2dbc.connection.init;

import java.util.UUID;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Integration tests for {@link DatabasePopulator} using H2.
 *
 * @author Mark Paluch
 */
public class H2DatabasePopulatorIntegrationTests
		extends AbstractDatabaseInitializationTests {

	UUID databaseName = UUID.randomUUID();

	ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///"
			+ databaseName + "?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");


	@Override
	ConnectionFactory getConnectionFactory() {
		return this.connectionFactory;
	}

	@Test
	public void shouldRunScript() {

		databasePopulator.addScript(usersSchema());
		databasePopulator.addScript(resource("db-test-data-h2.sql"));
		// Set statement separator to double newline so that ";" is not
		// considered a statement separator within the source code of the
		// aliased function 'REVERSE'.
		databasePopulator.setSeparator("\n\n");

		databasePopulator.populate(connectionFactory).as(
				StepVerifier::create).verifyComplete();

		assertUsersDatabaseCreated(connectionFactory, "White");
	}

}
