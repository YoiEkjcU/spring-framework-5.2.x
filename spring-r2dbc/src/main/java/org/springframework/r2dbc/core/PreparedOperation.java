package org.springframework.r2dbc.core;

import java.util.function.Supplier;

import org.springframework.r2dbc.core.binding.BindTarget;

/**
 * Extension to {@link QueryOperation} for a prepared SQL query
 * {@link Supplier} with bound parameters. Contains parameter
 * bindings that can be {@link #bindTo bound} bound to a {@link BindTarget}.
 * <p>Can be executed with {@link org.springframework.r2dbc.core.DatabaseClient}.
 *
 * @param <T> underlying operation source.
 * @author Mark Paluch
 * @see org.springframework.r2dbc.core.DatabaseClient#sql(Supplier)
 * @since 5.3
 */
public interface PreparedOperation<T> extends QueryOperation {

	/**
	 * Return the underlying query source.
	 *
	 * @return the query source, such as a statement/criteria object.
	 */
	T getSource();

	/**
	 * Apply bindings to {@link BindTarget}.
	 *
	 * @param target the target to apply bindings to.
	 */
	void bindTo(BindTarget target);

}
