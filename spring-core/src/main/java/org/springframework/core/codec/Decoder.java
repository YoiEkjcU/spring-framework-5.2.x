package org.springframework.core.codec;

import java.util.List;
import java.util.Map;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.publisher.Sinks;

import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

/**
 * Strategy for decoding a {@link DataBuffer} input stream into an output stream
 * of elements of type {@code <T>}.
 *
 * @param <T> the type of elements in the output stream
 * @author Sebastien Deleuze
 * @author Rossen Stoyanchev
 * @since 5.0
 */
public interface Decoder<T> {

	/**
	 * Whether the decoder supports the given target element type and the MIME
	 * type of the source stream.
	 *
	 * @param elementType the target element type for the output stream
	 * @param mimeType    the mime type associated with the stream to decode
	 *                    (can be {@code null} if not specified)
	 * @return {@code true} if supported, {@code false} otherwise
	 */
	boolean canDecode(ResolvableType elementType, @Nullable MimeType mimeType);

	/**
	 * Decode a {@link DataBuffer} input stream into a Flux of {@code T}.
	 *
	 * @param inputStream the {@code DataBuffer} input stream to decode
	 * @param elementType the expected type of elements in the output stream;
	 *                    this type must have been previously passed to the {@link #canDecode}
	 *                    method and it must have returned {@code true}.
	 * @param mimeType    the MIME type associated with the input stream (optional)
	 * @param hints       additional information about how to do encode
	 * @return the output stream with decoded elements
	 */
	Flux<T> decode(Publisher<DataBuffer> inputStream, ResolvableType elementType,
				   @Nullable MimeType mimeType, @Nullable Map<String, Object> hints);

	/**
	 * Decode a {@link DataBuffer} input stream into a Mono of {@code T}.
	 *
	 * @param inputStream the {@code DataBuffer} input stream to decode
	 * @param elementType the expected type of elements in the output stream;
	 *                    this type must have been previously passed to the {@link #canDecode}
	 *                    method and it must have returned {@code true}.
	 * @param mimeType    the MIME type associated with the input stream (optional)
	 * @param hints       additional information about how to do encode
	 * @return the output stream with the decoded element
	 */
	Mono<T> decodeToMono(Publisher<DataBuffer> inputStream, ResolvableType elementType,
						 @Nullable MimeType mimeType, @Nullable Map<String, Object> hints);

	/**
	 * Decode a data buffer to an Object of type T. This is useful for scenarios,
	 * that distinct messages (or events) are decoded and handled individually,
	 * in fully aggregated form.
	 *
	 * @param buffer     the {@code DataBuffer} to decode
	 * @param targetType the expected output type
	 * @param mimeType   the MIME type associated with the data
	 * @param hints      additional information about how to do encode
	 * @return the decoded value, possibly {@code null}
	 * @since 5.2
	 */
	@Nullable
	default T decode(DataBuffer buffer, ResolvableType targetType,
					 @Nullable MimeType mimeType, @Nullable Map<String, Object> hints) throws DecodingException {

		MonoProcessor<T> processor = MonoProcessor.fromSink(Sinks.one());
		decodeToMono(Mono.just(buffer), targetType, mimeType, hints).subscribeWith(processor);

		Assert.state(processor.isTerminated(), "DataBuffer decoding should have completed.");
		Throwable ex = processor.getError();
		if (ex != null) {
			throw (ex instanceof CodecException ? (CodecException) ex :
					new DecodingException("Failed to decode: " + ex.getMessage(), ex));
		}
		return processor.peek();
	}

	/**
	 * Return the list of MIME types this decoder supports.
	 */
	List<MimeType> getDecodableMimeTypes();

}
