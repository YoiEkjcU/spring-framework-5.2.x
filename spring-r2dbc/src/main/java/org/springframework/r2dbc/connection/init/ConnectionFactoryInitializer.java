package org.springframework.r2dbc.connection.init;

import io.r2dbc.spi.ConnectionFactory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Used to {@linkplain #setDatabasePopulator set up} a database during
 * initialization and {@link #setDatabaseCleaner clean up} a database during
 * destruction.
 *
 * @author Mark Paluch
 * @see DatabasePopulator
 * @since 5.3
 */
public class ConnectionFactoryInitializer implements InitializingBean, DisposableBean {

	@Nullable
	private ConnectionFactory connectionFactory;

	@Nullable
	private DatabasePopulator databasePopulator;

	@Nullable
	private DatabasePopulator databaseCleaner;

	private boolean enabled = true;


	/**
	 * The {@link ConnectionFactory} for the database to populate when this component is initialized and to clean up when
	 * this component is shut down.
	 * <p/>
	 * This property is mandatory with no default provided.
	 *
	 * @param connectionFactory the R2DBC {@link ConnectionFactory}.
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	/**
	 * Set the {@link DatabasePopulator} to execute during the bean initialization phase.
	 *
	 * @param databasePopulator the {@link DatabasePopulator} to use during initialization
	 * @see #setDatabaseCleaner
	 */
	public void setDatabasePopulator(DatabasePopulator databasePopulator) {
		this.databasePopulator = databasePopulator;
	}

	/**
	 * Set the {@link DatabasePopulator} to execute during the bean destruction phase, cleaning up the database and
	 * leaving it in a known state for others.
	 *
	 * @param databaseCleaner the {@link DatabasePopulator} to use during destruction
	 * @see #setDatabasePopulator
	 */
	public void setDatabaseCleaner(DatabasePopulator databaseCleaner) {
		this.databaseCleaner = databaseCleaner;
	}

	/**
	 * Flag to explicitly enable or disable the {@link #setDatabasePopulator database populator} and
	 * {@link #setDatabaseCleaner database cleaner}.
	 *
	 * @param enabled {@code true} if the database populator and database cleaner should be called on startup and
	 *                shutdown, respectively
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Use the {@link #setDatabasePopulator database populator} to set up the database.
	 */
	@Override
	public void afterPropertiesSet() {
		execute(this.databasePopulator);
	}

	/**
	 * Use the {@link #setDatabaseCleaner database cleaner} to clean up the database.
	 */
	@Override
	public void destroy() {
		execute(this.databaseCleaner);
	}

	private void execute(@Nullable DatabasePopulator populator) {
		Assert.state(this.connectionFactory != null, "ConnectionFactory must be set");
		if (this.enabled && populator != null) {
			populator.populate(this.connectionFactory).block();
		}
	}

}
