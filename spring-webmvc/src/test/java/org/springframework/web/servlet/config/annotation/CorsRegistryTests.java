package org.springframework.web.servlet.config.annotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.cors.CorsConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test fixture with a {@link CorsRegistry}.
 *
 * @author Sebastien Deleuze
 */
public class CorsRegistryTests {

	private CorsRegistry registry;

	@BeforeEach
	public void setUp() {
		this.registry = new CorsRegistry();
	}

	@Test
	public void noMapping() {
		assertThat(this.registry.getCorsConfigurations().isEmpty()).isTrue();
	}

	@Test
	public void multipleMappings() {
		this.registry.addMapping("/foo");
		this.registry.addMapping("/bar");
		assertThat(this.registry.getCorsConfigurations().size()).isEqualTo(2);
	}

	@Test
	public void customizedMapping() {
		this.registry.addMapping("/foo").allowedOrigins("https://domain2.com", "https://domain2.com")
				.allowedMethods("DELETE").allowCredentials(false).allowedHeaders("header1", "header2")
				.exposedHeaders("header3", "header4").maxAge(3600);
		Map<String, CorsConfiguration> configs = this.registry.getCorsConfigurations();
		assertThat(configs.size()).isEqualTo(1);
		CorsConfiguration config = configs.get("/foo");
		assertThat(config.getAllowedOrigins()).isEqualTo(Arrays.asList("https://domain2.com", "https://domain2.com"));
		assertThat(config.getAllowedMethods()).isEqualTo(Collections.singletonList("DELETE"));
		assertThat(config.getAllowedHeaders()).isEqualTo(Arrays.asList("header1", "header2"));
		assertThat(config.getExposedHeaders()).isEqualTo(Arrays.asList("header3", "header4"));
		assertThat(config.getAllowCredentials()).isEqualTo(false);
		assertThat(config.getMaxAge()).isEqualTo(Long.valueOf(3600));
	}

	@Test
	public void allowCredentials() {
		this.registry.addMapping("/foo").allowCredentials(true);
		CorsConfiguration config = this.registry.getCorsConfigurations().get("/foo");
		assertThat(config.getAllowedOrigins())
				.as("Globally origins=\"*\" and allowCredentials=true should be possible")
				.containsExactly("*");
	}
}
