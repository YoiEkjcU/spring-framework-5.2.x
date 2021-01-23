package org.springframework.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A strategy interface for converting from data in an InputStream to an Object.
 *
 * @param <T> the object type
 * @author Gary Russell
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @see Serializer
 * @since 3.0.5
 */
@FunctionalInterface
public interface Deserializer<T> {

	/**
	 * Read (assemble) an object of type T from the given InputStream.
	 * <p>Note: Implementations should not close the given InputStream
	 * (or any decorators of that InputStream) but rather leave this up
	 * to the caller.
	 *
	 * @param inputStream the input stream
	 * @return the deserialized object
	 * @throws IOException in case of errors reading from the stream
	 */
	T deserialize(InputStream inputStream) throws IOException;

	/**
	 * Read (assemble) an object of type T from the given byte array.
	 *
	 * @param serialized the byte array
	 * @return the deserialized object
	 * @throws IOException in case of deserialization failure
	 * @since 5.2.7
	 */
	default T deserializeFromByteArray(byte[] serialized) throws IOException {
		return deserialize(new ByteArrayInputStream(serialized));
	}

}
