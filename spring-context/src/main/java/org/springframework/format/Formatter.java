package org.springframework.format;

/**
 * Formats objects of type T.
 * A Formatter is both a Printer <i>and</i> a Parser for an object type.
 *
 * @param <T> the type of object this Formatter formats
 * @author Keith Donald
 * @since 3.0
 */
public interface Formatter<T> extends Printer<T>, Parser<T> {

}
