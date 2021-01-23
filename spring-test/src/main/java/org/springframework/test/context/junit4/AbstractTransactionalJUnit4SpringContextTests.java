package org.springframework.test.context.junit4;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.lang.Nullable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.event.EventPublishingTestExecutionListener;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Abstract {@linkplain Transactional transactional} extension of
 * {@link AbstractJUnit4SpringContextTests} which adds convenience functionality
 * for JDBC access. Expects a {@link DataSource} bean and a
 * {@link PlatformTransactionManager} bean to be defined in the Spring
 * {@linkplain ApplicationContext application context}.
 *
 * <p>This class exposes a {@link JdbcTemplate} and provides an easy way to
 * {@linkplain #countRowsInTable count the number of rows in a table}
 * (potentially {@linkplain #countRowsInTableWhere with a WHERE clause}),
 * {@linkplain #deleteFromTables delete from tables},
 * {@linkplain #dropTables drop tables}, and
 * {@linkplain #executeSqlScript execute SQL scripts} within a transaction.
 *
 * <p>Concrete subclasses must fulfill the same requirements outlined in
 * {@link AbstractJUnit4SpringContextTests}.
 *
 * <p>The following {@link org.springframework.test.context.TestExecutionListener
 * TestExecutionListeners} are configured by default:
 *
 * <ul>
 * <li>{@link org.springframework.test.context.web.ServletTestExecutionListener}
 * <li>{@link org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener}
 * <li>{@link org.springframework.test.context.support.DependencyInjectionTestExecutionListener}
 * <li>{@link org.springframework.test.context.support.DirtiesContextTestExecutionListener}
 * <li>{@link org.springframework.test.context.transaction.TransactionalTestExecutionListener}
 * <li>{@link org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener}
 * <li>{@link org.springframework.test.context.event.EventPublishingTestExecutionListener}
 * </ul>
 *
 * <p>This class serves only as a convenience for extension.
 * <ul>
 * <li>If you do not wish for your test classes to be tied to a Spring-specific
 * class hierarchy, you may configure your own custom test classes by using
 * {@link SpringRunner}, {@link ContextConfiguration @ContextConfiguration},
 * {@link TestExecutionListeners @TestExecutionListeners}, etc.</li>
 * <li>If you wish to extend this class and use a runner other than the
 * {@link SpringRunner}, as of Spring Framework 4.2 you can use
 * {@link org.springframework.test.context.junit4.rules.SpringClassRule SpringClassRule} and
 * {@link org.springframework.test.context.junit4.rules.SpringMethodRule SpringMethodRule}
 * and specify your runner of choice via {@link org.junit.runner.RunWith @RunWith(...)}.</li>
 * </ul>
 *
 * <p><strong>NOTE:</strong> This class requires JUnit 4.12 or higher.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @see AbstractJUnit4SpringContextTests
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.TestExecutionListeners
 * @see org.springframework.test.context.transaction.TransactionalTestExecutionListener
 * @see org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener
 * @see org.springframework.transaction.annotation.Transactional
 * @see org.springframework.test.annotation.Commit
 * @see org.springframework.test.annotation.Rollback
 * @see org.springframework.test.context.transaction.BeforeTransaction
 * @see org.springframework.test.context.transaction.AfterTransaction
 * @see org.springframework.test.jdbc.JdbcTestUtils
 * @see org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests
 * @since 2.5
 */
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
		DirtiesContextBeforeModesTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
		SqlScriptsTestExecutionListener.class, EventPublishingTestExecutionListener.class}, inheritListeners = false)
@Transactional
public abstract class AbstractTransactionalJUnit4SpringContextTests extends AbstractJUnit4SpringContextTests {

	/**
	 * The {@code JdbcTemplate} that this base class manages, available to subclasses.
	 *
	 * @since 3.2
	 */
	protected final JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Nullable
	private String sqlScriptEncoding;


	/**
	 * Set the {@code DataSource}, typically provided via Dependency Injection.
	 * <p>This method also instantiates the {@link #jdbcTemplate} instance variable.
	 */
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate.setDataSource(dataSource);
	}

	/**
	 * Specify the encoding for SQL scripts, if different from the platform encoding.
	 *
	 * @see #executeSqlScript
	 */
	public void setSqlScriptEncoding(String sqlScriptEncoding) {
		this.sqlScriptEncoding = sqlScriptEncoding;
	}

	/**
	 * Convenience method for counting the rows in the given table.
	 *
	 * @param tableName table name to count rows in
	 * @return the number of rows in the table
	 * @see JdbcTestUtils#countRowsInTable
	 */
	protected int countRowsInTable(String tableName) {
		return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
	}

	/**
	 * Convenience method for counting the rows in the given table, using the
	 * provided {@code WHERE} clause.
	 * <p>See the Javadoc for {@link JdbcTestUtils#countRowsInTableWhere} for details.
	 *
	 * @param tableName   the name of the table to count rows in
	 * @param whereClause the {@code WHERE} clause to append to the query
	 * @return the number of rows in the table that match the provided
	 * {@code WHERE} clause
	 * @see JdbcTestUtils#countRowsInTableWhere
	 * @since 3.2
	 */
	protected int countRowsInTableWhere(String tableName, String whereClause) {
		return JdbcTestUtils.countRowsInTableWhere(this.jdbcTemplate, tableName, whereClause);
	}

	/**
	 * Convenience method for deleting all rows from the specified tables.
	 * <p>Use with caution outside of a transaction!
	 *
	 * @param names the names of the tables from which to delete
	 * @return the total number of rows deleted from all specified tables
	 * @see JdbcTestUtils#deleteFromTables
	 */
	protected int deleteFromTables(String... names) {
		return JdbcTestUtils.deleteFromTables(this.jdbcTemplate, names);
	}

	/**
	 * Convenience method for deleting all rows from the given table, using the
	 * provided {@code WHERE} clause.
	 * <p>Use with caution outside of a transaction!
	 * <p>See the Javadoc for {@link JdbcTestUtils#deleteFromTableWhere} for details.
	 *
	 * @param tableName   the name of the table to delete rows from
	 * @param whereClause the {@code WHERE} clause to append to the query
	 * @param args        arguments to bind to the query (leaving it to the {@code
	 *                    PreparedStatement} to guess the corresponding SQL type); may also contain
	 *                    {@link org.springframework.jdbc.core.SqlParameterValue SqlParameterValue}
	 *                    objects which indicate not only the argument value but also the SQL type
	 *                    and optionally the scale.
	 * @return the number of rows deleted from the table
	 * @see JdbcTestUtils#deleteFromTableWhere
	 * @since 4.0
	 */
	protected int deleteFromTableWhere(String tableName, String whereClause, Object... args) {
		return JdbcTestUtils.deleteFromTableWhere(this.jdbcTemplate, tableName, whereClause, args);
	}

	/**
	 * Convenience method for dropping all of the specified tables.
	 * <p>Use with caution outside of a transaction!
	 *
	 * @param names the names of the tables to drop
	 * @see JdbcTestUtils#dropTables
	 * @since 3.2
	 */
	protected void dropTables(String... names) {
		JdbcTestUtils.dropTables(this.jdbcTemplate, names);
	}

	/**
	 * Execute the given SQL script.
	 * <p>Use with caution outside of a transaction!
	 * <p>The script will normally be loaded by classpath.
	 * <p><b>Do not use this method to execute DDL if you expect rollback.</b>
	 *
	 * @param sqlResourcePath the Spring resource path for the SQL script
	 * @param continueOnError whether or not to continue without throwing an
	 *                        exception in the event of an error
	 * @throws DataAccessException if there is an error executing a statement
	 * @see ResourceDatabasePopulator
	 * @see #setSqlScriptEncoding
	 */
	protected void executeSqlScript(String sqlResourcePath, boolean continueOnError) throws DataAccessException {
		DataSource ds = this.jdbcTemplate.getDataSource();
		Assert.state(ds != null, "No DataSource set");
		Assert.state(this.applicationContext != null, "No ApplicationContext set");
		Resource resource = this.applicationContext.getResource(sqlResourcePath);
		new ResourceDatabasePopulator(continueOnError, false, this.sqlScriptEncoding, resource).execute(ds);
	}

}
