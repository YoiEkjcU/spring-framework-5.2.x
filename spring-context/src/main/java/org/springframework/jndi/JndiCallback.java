package org.springframework.jndi;

import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.lang.Nullable;

/**
 * Callback interface to be implemented by classes that need to perform an
 * operation (such as a lookup) in a JNDI context. This callback approach
 * is valuable in simplifying error handling, which is performed by the
 * JndiTemplate class. This is a similar to JdbcTemplate's approach.
 *
 * <p>Note that there is hardly any need to implement this callback
 * interface, as JndiTemplate provides all usual JNDI operations via
 * convenience methods.
 *
 * @param <T> the resulting object type
 * @author Rod Johnson
 * @see JndiTemplate
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
@FunctionalInterface
public interface JndiCallback<T> {

	/**
	 * Do something with the given JNDI context.
	 * <p>Implementations don't need to worry about error handling
	 * or cleanup, as the JndiTemplate class will handle this.
	 *
	 * @param ctx the current JNDI context
	 * @return a result object, or {@code null}
	 * @throws NamingException if thrown by JNDI methods
	 */
	@Nullable
	T doInContext(Context ctx) throws NamingException;

}

