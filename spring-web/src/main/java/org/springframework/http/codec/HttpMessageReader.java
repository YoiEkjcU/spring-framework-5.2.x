package org.springframework.http.codec;

import java.util.List;
import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;

/**
 * Strategy for reading from a {@link ReactiveHttpInputMessage} and decoding
 * the stream of bytes to Objects of type {@code <T>}.
 *
 * @param <T> the type of objects in the decoded output stream
 * @author Rossen Stoyanchev
 * @author Arjen Poutsma
 * @author Sebastien Deleuze
 * @since 5.0
 */
public interface HttpMessageReader<T> {

	/**
	 * Return the {@link MediaType}'s that this reader supports.
	 */
	List<MediaType> getReadableMediaTypes();

	/**
	 * Whether the given object type is supported by this reader.
	 *
	 * @param elementType the type of object to check
	 * @param mediaType   the media type for the read (possibly {@code null})
	 * @return {@code true} if readable, {@code false} otherwise
	 */
	boolean canRead(ResolvableType elementType, @Nullable MediaType mediaType);

	/**
	 * Read from the input message and encode to a stream of objects.
	 *
	 * @param elementType the type of objects in the stream which must have been
	 *                    previously checked via {@link #canRead(ResolvableType, MediaType)}
	 * @param message     the message to read from
	 * @param hints       additional information about how to read and decode the input
	 * @return the decoded stream of elements
	 */
	Flux<T> read(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints);

	/**
	 * Read from the input message and encode to a single object.
	 *
	 * @param elementType the type of objects in the stream which must have been
	 *                    previously checked via {@link #canRead(ResolvableType, MediaType)}
	 * @param message     the message to read from
	 * @param hints       additional information about how to read and decode the input
	 * @return the decoded object
	 */
	Mono<T> readMono(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints);

	/**
	 * Server-side only alternative to
	 * {@link #read(ResolvableType, ReactiveHttpInputMessage, Map)}
	 * with additional context available.
	 *
	 * @param actualType  the actual type of the target method parameter;
	 *                    for annotated controllers, the {@link MethodParameter} can be accessed
	 *                    via {@link ResolvableType#getSource()}.
	 * @param elementType the type of Objects in the output stream
	 * @param request     the current request
	 * @param response    the current response
	 * @param hints       additional information about how to read the body
	 * @return the decoded stream of elements
	 */
	default Flux<T> read(ResolvableType actualType, ResolvableType elementType, ServerHttpRequest request,
						 ServerHttpResponse response, Map<String, Object> hints) {

		return read(elementType, request, hints);
	}

	/**
	 * Server-side only alternative to
	 * {@link #readMono(ResolvableType, ReactiveHttpInputMessage, Map)}
	 * with additional, context available.
	 *
	 * @param actualType  the actual type of the target method parameter;
	 *                    for annotated controllers, the {@link MethodParameter} can be accessed
	 *                    via {@link ResolvableType#getSource()}.
	 * @param elementType the type of Objects in the output stream
	 * @param request     the current request
	 * @param response    the current response
	 * @param hints       additional information about how to read the body
	 * @return the decoded stream of elements
	 */
	default Mono<T> readMono(ResolvableType actualType, ResolvableType elementType, ServerHttpRequest request,
							 ServerHttpResponse response, Map<String, Object> hints) {

		return readMono(elementType, request, hints);
	}

}
