package org.springframework.messaging.rsocket;

import java.util.Map;

import io.rsocket.Payload;

import org.springframework.core.codec.DecodingException;
import org.springframework.util.MimeType;

/**
 * Strategy to extract a map of value(s) from {@link Payload} metadata, which
 * could be composite metadata with multiple entries. Each metadata entry
 * is decoded based on its {@code MimeType} and a name is assigned to the decoded
 * value. The resulting name-value pairs can be added to the headers of a
 * {@link org.springframework.messaging.Message Message}.
 *
 * @author Rossen Stoyanchev
 * @see MetadataExtractorRegistry
 * @since 5.2
 */
public interface MetadataExtractor {

	/**
	 * The key to assign to the extracted "route" of the payload.
	 */
	String ROUTE_KEY = "route";


	/**
	 * Extract a map of values from the given {@link Payload} metadata.
	 * The Payload "route", if present, should be saved under {@link #ROUTE_KEY}.
	 *
	 * @param payload          the payload whose metadata should be read
	 * @param metadataMimeType the metadata MimeType for the connection.
	 * @return name values pairs extracted from the metadata
	 * @throws DecodingException        if the metadata cannot be decoded
	 * @throws IllegalArgumentException if routing metadata cannot be decoded
	 */
	Map<String, Object> extract(Payload payload, MimeType metadataMimeType);

}
