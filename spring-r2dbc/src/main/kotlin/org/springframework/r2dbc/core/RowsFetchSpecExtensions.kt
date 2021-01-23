package org.springframework.r2dbc.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.dao.EmptyResultDataAccessException

/**
 * Non-nullable Coroutines variant of [RowsFetchSpec.one].
 *
 * @author Sebastien Deleuze
 */
suspend fun <T> RowsFetchSpec<T>.awaitOne(): T {
	return one().awaitFirstOrNull() ?: throw EmptyResultDataAccessException(1)
}

/**
 * Nullable Coroutines variant of [RowsFetchSpec.one].
 *
 * @author Sebastien Deleuze
 */
suspend fun <T> RowsFetchSpec<T>.awaitOneOrNull(): T? =
		one().awaitFirstOrNull()

/**
 * Non-nullable Coroutines variant of [RowsFetchSpec.first].
 *
 * @author Sebastien Deleuze
 */
suspend fun <T> RowsFetchSpec<T>.awaitFirst(): T {
	return first().awaitFirstOrNull() ?: throw EmptyResultDataAccessException(1)
}

/**
 * Nullable Coroutines variant of [RowsFetchSpec.first].
 *
 * @author Sebastien Deleuze
 */
suspend fun <T> RowsFetchSpec<T>.awaitFirstOrNull(): T? =
		first().awaitFirstOrNull()

/**
 * Coroutines [Flow] variant of [RowsFetchSpec.all].
 *
 * @author Sebastien Deleuze
 */
fun <T : Any> RowsFetchSpec<T>.flow(): Flow<T> = all().asFlow()
