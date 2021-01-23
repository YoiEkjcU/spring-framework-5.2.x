package org.springframework.r2dbc.core;

import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

/**
 * Integration tests for {@link DatabaseClient} against H2.
 *
 * @author Mark Paluch
 */
public class H2TransactionalDatabaseClientIntegrationTests
		extends AbstractTransactionalDatabaseClientIntegrationTests {

	public static String CREATE_TABLE_LEGOSET = "CREATE TABLE legoset (\n" //
			+ "    id          integer CONSTRAINT id PRIMARY KEY,\n" //
			+ "    version     integer NULL,\n" //
			+ "    name        varchar(255) NOT NULL,\n" //
			+ "    manual      integer NULL\n" //
			+ ");";

	@Override
	protected ConnectionFactory createConnectionFactory() {
		return H2ConnectionFactory.inMemory("r2dbc-transactional");
	}

	@Override
	protected String getCreateTableStatement() {
		return CREATE_TABLE_LEGOSET;
	}
}
