package org.springframework.r2dbc.core.binding;

import io.r2dbc.spi.Statement;

/**
 * A bind marker represents a single bindable parameter within a query.
 * Bind markers are dialect-specific and provide a
 * {@link #getPlaceholder() placeholder} that is used in the actual query.
 *
 * @author Mark Paluch
 * @see Statement#bind
 * @see BindMarkers
 * @see BindMarkersFactory
 * @since 5.3
 */
public interface BindMarker {

	/**
	 * Return the database-specific placeholder for a given substitution.
	 */
	String getPlaceholder();

	/**
	 * Bind the given {@code value} to the {@link Statement} using the underlying binding strategy.
	 *
	 * @param bindTarget the target to bind the value to
	 * @param value      the actual value. Must not be {@code null}
	 *                   Use {@link #bindNull(BindTarget, Class)} for {@code null} values
	 * @see Statement#bind
	 */
	void bind(BindTarget bindTarget, Object value);

	/**
	 * Bind a {@code null} value to the {@link Statement} using the underlying binding strategy.
	 *
	 * @param bindTarget the target to bind the value to
	 * @param valueType  value type, must not be {@code null}
	 * @see Statement#bindNull
	 */
	void bindNull(BindTarget bindTarget, Class<?> valueType);

}
