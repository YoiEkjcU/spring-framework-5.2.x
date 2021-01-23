package org.springframework.r2dbc.core;

import java.util.function.Supplier;

/**
 * Interface declaring a query operation that can be represented
 * with a query string. This interface is typically implemented
 * by classes representing an SQL operation such as {@code SELECT},
 * {@code INSERT}, and such.
 *
 * @author Mark Paluch
 * @see PreparedOperation
 * @since 5.3
 */
@FunctionalInterface
public interface QueryOperation extends Supplier<String> {

	/**
	 * Returns the string-representation of this operation to
	 * be used with {@link io.r2dbc.spi.Statement} creation.
	 *
	 * @return the operation as SQL string
	 * @see io.r2dbc.spi.Connection#createStatement(String)
	 */
	String toQuery();

	@Override
	default String get() {
		return toQuery();
	}

}
