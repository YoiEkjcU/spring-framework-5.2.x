package org.springframework.messaging.rsocket;

import java.util.Map;
import java.util.function.BiConsumer;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;

/**
 * Stores registrations of extractors for metadata entries.
 * Each metadata entry is decoded based on its {@code MimeType} and a name
 * is assigned to the decoded value.
 *
 * @author Rossen Stoyanchev
 * @author Brian Clozel
 * @see MetadataExtractor
 * @since 5.2
 */
public interface MetadataExtractorRegistry {

	/**
	 * Decode metadata entries with the given {@link MimeType} to the specified
	 * target class, and store the decoded value in the output map under the
	 * given name.
	 *
	 * @param mimeType   the mime type of metadata entries to extract
	 * @param targetType the target value type to decode to
	 * @param name       assign a name for the decoded value; if not provided, then
	 *                   the mime type is used as the key
	 */
	default void metadataToExtract(MimeType mimeType, Class<?> targetType, @Nullable String name) {
		String key = name != null ? name : mimeType.toString();
		metadataToExtract(mimeType, targetType, (value, map) -> map.put(key, value));
	}

	/**
	 * Variant of {@link #metadataToExtract(MimeType, Class, String)} that accepts
	 * {@link ParameterizedTypeReference} instead of {@link Class} for
	 * specifying a target type with generic parameters.
	 *
	 * @param mimeType   the mime type of metadata entries to extract
	 * @param targetType the target value type to decode to
	 */
	default void metadataToExtract(
			MimeType mimeType, ParameterizedTypeReference<?> targetType, @Nullable String name) {

		String key = name != null ? name : mimeType.toString();
		metadataToExtract(mimeType, targetType, (value, map) -> map.put(key, value));
	}

	/**
	 * Variant of {@link #metadataToExtract(MimeType, Class, String)} that allows
	 * custom logic to be used to map the decoded value to any number of values
	 * in the output map.
	 *
	 * @param mimeType   the mime type of metadata entries to extract
	 * @param targetType the target value type to decode to
	 * @param mapper     custom logic to add the decoded value to the output map
	 * @param <T>        the target value type
	 */
	<T> void metadataToExtract(
			MimeType mimeType, Class<T> targetType, BiConsumer<T, Map<String, Object>> mapper);

	/**
	 * Variant of {@link #metadataToExtract(MimeType, Class, BiConsumer)} that
	 * accepts {@link ParameterizedTypeReference} instead of {@link Class} for
	 * specifying a target type with generic parameters.
	 *
	 * @param mimeType the mime type of metadata entries to extract
	 * @param type     the target value type to decode to
	 * @param mapper   custom logic to add the decoded value to the output map
	 * @param <T>      the target value type
	 */
	<T> void metadataToExtract(
			MimeType mimeType, ParameterizedTypeReference<T> type, BiConsumer<T, Map<String, Object>> mapper);

}
