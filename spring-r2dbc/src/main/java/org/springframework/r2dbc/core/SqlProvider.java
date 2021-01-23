package org.springframework.r2dbc.core;

import org.springframework.lang.Nullable;

/**
 * Interface to be implemented by objects that can provide SQL strings.
 *
 * <p>Typically implemented by objects that want to expose the SQL they
 * use to create their statements, to allow for better contextual
 * information in case of exceptions.
 *
 * @author Mark Paluch
 * @since 5.3
 */
public interface SqlProvider {

	/**
	 * Return the SQL string for this object, i.e.
	 * typically the SQL used for creating statements.
	 *
	 * @return the SQL string, or {@code null}
	 */
	@Nullable
	String getSql();

}
