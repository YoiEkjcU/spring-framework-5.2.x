package org.springframework.http.codec.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

/**
 * Decode a byte stream into Smile and convert to Object's with Jackson 2.9,
 * leveraging non-blocking parsing.
 *
 * @author Sebastien Deleuze
 * @author Rossen Stoyanchev
 * @see Jackson2JsonEncoder
 * @since 5.0
 */
public class Jackson2SmileDecoder extends AbstractJackson2Decoder {

	private static final MimeType[] DEFAULT_SMILE_MIME_TYPES = new MimeType[]{
			new MimeType("application", "x-jackson-smile"),
			new MimeType("application", "*+x-jackson-smile")};


	public Jackson2SmileDecoder() {
		this(Jackson2ObjectMapperBuilder.smile().build(), DEFAULT_SMILE_MIME_TYPES);
	}

	public Jackson2SmileDecoder(ObjectMapper mapper, MimeType... mimeTypes) {
		super(mapper, mimeTypes);
		Assert.isAssignable(SmileFactory.class, mapper.getFactory().getClass());
	}

}
