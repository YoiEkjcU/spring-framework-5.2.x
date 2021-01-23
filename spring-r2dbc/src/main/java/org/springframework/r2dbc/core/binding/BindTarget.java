package org.springframework.r2dbc.core.binding;

/**
 * Target to apply bindings to.
 *
 * @author Mark Paluch
 * @see io.r2dbc.spi.Statement#bind
 * @see io.r2dbc.spi.Statement#bindNull
 * @since 5.3
 */
public interface BindTarget {

	/**
	 * Bind a value.
	 *
	 * @param identifier the identifier to bind to
	 * @param value      the value to bind
	 */
	void bind(String identifier, Object value);

	/**
	 * Bind a value to an index. Indexes are zero-based.
	 *
	 * @param index the index to bind to
	 * @param value the value to bind
	 */
	void bind(int index, Object value);

	/**
	 * Bind a {@code null} value.
	 *
	 * @param identifier the identifier to bind to
	 * @param type       the type of {@code null} value
	 */
	void bindNull(String identifier, Class<?> type);

	/**
	 * Bind a {@code null} value.
	 *
	 * @param index the index to bind to
	 * @param type  the type of {@code null} value
	 */
	void bindNull(int index, Class<?> type);

}
