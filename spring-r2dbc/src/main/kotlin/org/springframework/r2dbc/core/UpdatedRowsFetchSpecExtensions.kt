package org.springframework.r2dbc.core

import kotlinx.coroutines.reactive.awaitSingle

/**
 * Coroutines variant of [UpdatedRowsFetchSpec.rowsUpdated].
 *
 * @author Fred Montariol
 */
suspend fun UpdatedRowsFetchSpec.awaitRowsUpdated(): Int =
		rowsUpdated().awaitSingle()
