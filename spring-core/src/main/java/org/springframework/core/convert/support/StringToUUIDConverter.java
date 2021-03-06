package org.springframework.core.convert.support;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * Converts from a String to a {@link java.util.UUID}.
 *
 * @author Phillip Webb
 * @see UUID#fromString
 * @since 3.2
 */
final class StringToUUIDConverter implements Converter<String, UUID> {

	@Override
	public UUID convert(String source) {
		return (StringUtils.hasText(source) ? UUID.fromString(source.trim()) : null);
	}

}
