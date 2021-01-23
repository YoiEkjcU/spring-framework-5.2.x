package org.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.springframework.core.convert.converter.Converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

/**
 * Unit tests for {@link IntegerToEnumConverterFactory}.
 *
 * @author Adilson Antunes
 * @author Sam Brannen
 */
class IntegerToEnumConverterFactoryTests {

	private final Converter<Integer, Color> converter = new IntegerToEnumConverterFactory().getConverter(Color.class);


	@ParameterizedTest
	@CsvSource({
		"0, RED",
		"1, BLUE",
		"2, GREEN"
	})
	void convertsIntegerToEnum(int index, Color color) {
		assertThat(converter.convert(index)).isEqualTo(color);
	}

	@Test
	void throwsArrayIndexOutOfBoundsExceptionIfInvalidEnumInteger() {
		assertThatExceptionOfType(ArrayIndexOutOfBoundsException.class)
				.isThrownBy(() -> converter.convert(999));
	}


	enum Color {
		RED,
		BLUE,
		GREEN
	}

}
