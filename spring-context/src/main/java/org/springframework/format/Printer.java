package org.springframework.format;

import java.util.Locale;

/**
 * Prints objects of type T for display.
 *
 * @param <T> the type of object this Printer prints
 * @author Keith Donald
 * @since 3.0
 */
@FunctionalInterface
public interface Printer<T> {

	/**
	 * Print the object of type T for display.
	 *
	 * @param object the instance to print
	 * @param locale the current user locale
	 * @return the printed text string
	 */
	String print(T object, Locale locale);

}
