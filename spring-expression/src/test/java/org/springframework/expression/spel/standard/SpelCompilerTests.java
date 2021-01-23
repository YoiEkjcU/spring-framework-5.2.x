package org.springframework.expression.spel.standard;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import org.springframework.core.Ordered;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link SpelCompiler}.
 *
 * @author Sam Brannen
 * @since 5.1.14
 */
class SpelCompilerTests {

	@Test // gh-24357
	void expressionCompilesWhenMethodComesFromPublicInterface() {
		SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, null);
		SpelExpressionParser parser = new SpelExpressionParser(config);

		OrderedComponent component = new OrderedComponent();
		Expression expression = parser.parseExpression("order");

		// Evaluate the expression multiple times to ensure that it gets compiled.
		IntStream.rangeClosed(1, 5).forEach(i -> assertThat(expression.getValue(component)).isEqualTo(42));
	}


	static class OrderedComponent implements Ordered {

		@Override
		public int getOrder() {
			return 42;
		}
	}

}
