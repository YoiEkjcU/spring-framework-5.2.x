package org.springframework.r2dbc.core;

import org.springframework.lang.Nullable;

/**
 * Interface that defines common functionality for objects
 * that can offer parameter values for named bind parameters,
 * serving as argument for {@link NamedParameterExpander} operations.
 *
 * <p>This interface allows for the specification of the type in
 * addition to parameter values. All parameter values and types are
 * identified by specifying the name of the parameter.
 *
 * <p>Intended to wrap various implementations like a {@link java.util.Map}
 * with a consistent interface.
 *
 * @author Mark Paluch
 * @see MapBindParameterSource
 * @since 5.3
 */
interface BindParameterSource {

	/**
	 * Determine whether there is a value for the specified named parameter.
	 *
	 * @param paramName the name of the parameter
	 * @return {@code true} if there is a value defined; {@code false} otherwise
	 */
	boolean hasValue(String paramName);

	/**
	 * Return the parameter value for the requested named parameter.
	 *
	 * @param paramName the name of the parameter
	 * @return the value of the specified parameter, can be {@code null}
	 * @throws IllegalArgumentException if there is no value
	 *                                  for the requested parameter
	 */
	@Nullable
	Object getValue(String paramName) throws IllegalArgumentException;

	/**
	 * Determine the type for the specified named parameter.
	 *
	 * @param paramName the name of the parameter
	 * @return the type of the specified parameter, or
	 * {@link Object#getClass()} if not known.
	 */
	default Class<?> getType(String paramName) {
		return Object.class;
	}

	/**
	 * Return parameter names of the underlying parameter source.
	 *
	 * @return parameter names of the underlying parameter source.
	 */
	Iterable<String> getParameterNames();

}
