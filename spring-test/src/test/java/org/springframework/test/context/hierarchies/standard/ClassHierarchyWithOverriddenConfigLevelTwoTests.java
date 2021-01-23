package org.springframework.test.context.hierarchies.standard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sam Brannen
 * @since 3.2.2
 */
@ExtendWith(SpringExtension.class)
@ContextHierarchy(@ContextConfiguration(name = "child", classes = ClassHierarchyWithOverriddenConfigLevelTwoTests.TestUserConfig.class, inheritLocations = false))
class ClassHierarchyWithOverriddenConfigLevelTwoTests extends ClassHierarchyWithMergedConfigLevelOneTests {

	@Configuration
	static class TestUserConfig {

		@Autowired
		private ClassHierarchyWithMergedConfigLevelOneTests.AppConfig appConfig;


		@Bean
		String user() {
			return appConfig.parent() + " + test user";
		}

		@Bean
		String beanFromTestUserConfig() {
			return "from TestUserConfig";
		}
	}


	@Autowired
	private String beanFromTestUserConfig;


	@Test
	@Override
	void loadContextHierarchy() {
		assertThat(context).as("child ApplicationContext").isNotNull();
		assertThat(context.getParent()).as("parent ApplicationContext").isNotNull();
		assertThat(context.getParent().getParent()).as("grandparent ApplicationContext").isNull();
		assertThat(parent).isEqualTo("parent");
		assertThat(user).isEqualTo("parent + test user");
		assertThat(beanFromTestUserConfig).isEqualTo("from TestUserConfig");
		assertThat(beanFromUserConfig).as("Bean from UserConfig should not be present.").isNull();
	}

}
