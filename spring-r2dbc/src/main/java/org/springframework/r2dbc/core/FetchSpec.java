package org.springframework.r2dbc.core;

/**
 * Union type for fetching results.
 *
 * @param <T> the row result type
 * @author Mark Paluch
 * @see RowsFetchSpec
 * @see UpdatedRowsFetchSpec
 * @since 5.3
 */
public interface FetchSpec<T> extends RowsFetchSpec<T>, UpdatedRowsFetchSpec {
}
