package org.springframework.expression.spel;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.spel.support.StandardTypeLocator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Unit tests for type comparison
 *
 * @author Andy Clement
 */
public class StandardTypeLocatorTests {

	@Test
	public void testImports() throws EvaluationException {
		StandardTypeLocator locator = new StandardTypeLocator();
		assertThat(locator.findType("java.lang.Integer")).isEqualTo(Integer.class);
		assertThat(locator.findType("java.lang.String")).isEqualTo(String.class);

		List<String> prefixes = locator.getImportPrefixes();
		assertThat(prefixes.size()).isEqualTo(1);
		assertThat(prefixes.contains("java.lang")).isTrue();
		assertThat(prefixes.contains("java.util")).isFalse();

		assertThat(locator.findType("Boolean")).isEqualTo(Boolean.class);
		// currently does not know about java.util by default
//		assertEquals(java.util.List.class,locator.findType("List"));

		assertThatExceptionOfType(SpelEvaluationException.class).isThrownBy(() ->
				locator.findType("URL"))
			.satisfies(ex -> assertThat(ex.getMessageCode()).isEqualTo(SpelMessage.TYPE_NOT_FOUND));
		locator.registerImport("java.net");
		assertThat(locator.findType("URL")).isEqualTo(java.net.URL.class);
	}

}
