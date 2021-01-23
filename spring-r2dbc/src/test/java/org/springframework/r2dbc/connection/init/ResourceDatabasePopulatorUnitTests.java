package org.springframework.r2dbc.connection.init;

import org.junit.jupiter.api.Test;

import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.mock;

/**
 * Unit tests for {@link ResourceDatabasePopulator}.
 *
 * @author Mark Paluch
 */
public class ResourceDatabasePopulatorUnitTests {

	private static final Resource script1 = mock(Resource.class);

	private static final Resource script2 = mock(Resource.class);

	private static final Resource script3 = mock(Resource.class);


	@Test
	public void constructWithNullResource() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new ResourceDatabasePopulator((Resource) null));
	}

	@Test
	public void constructWithNullResourceArray() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new ResourceDatabasePopulator((Resource[]) null));
	}

	@Test
	public void constructWithResource() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(
				script1);
		assertThat(databasePopulator.scripts).hasSize(1);
	}

	@Test
	public void constructWithMultipleResources() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(
				script1, script2);
		assertThat(databasePopulator.scripts).hasSize(2);
	}

	@Test
	public void constructWithMultipleResourcesAndThenAddScript() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(
				script1, script2);
		assertThat(databasePopulator.scripts).hasSize(2);

		databasePopulator.addScript(script3);
		assertThat(databasePopulator.scripts).hasSize(3);
	}

	@Test
	public void addScriptsWithNullResource() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		assertThatIllegalArgumentException().isThrownBy(
				() -> databasePopulator.addScripts((Resource) null));
	}

	@Test
	public void addScriptsWithNullResourceArray() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		assertThatIllegalArgumentException().isThrownBy(
				() -> databasePopulator.addScripts((Resource[]) null));
	}

	@Test
	public void setScriptsWithNullResource() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		assertThatIllegalArgumentException().isThrownBy(
				() -> databasePopulator.setScripts((Resource) null));
	}

	@Test
	public void setScriptsWithNullResourceArray() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		assertThatIllegalArgumentException().isThrownBy(
				() -> databasePopulator.setScripts((Resource[]) null));
	}

	@Test
	public void setScriptsAndThenAddScript() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		assertThat(databasePopulator.scripts).isEmpty();

		databasePopulator.setScripts(script1, script2);
		assertThat(databasePopulator.scripts).hasSize(2);

		databasePopulator.addScript(script3);
		assertThat(databasePopulator.scripts).hasSize(3);
	}

}
