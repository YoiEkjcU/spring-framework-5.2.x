package org.springframework.r2dbc.core

import kotlinx.coroutines.reactive.awaitFirstOrNull

/**
 * Coroutines variant of [DatabaseClient.GenericExecuteSpec.then].
 *
 * @author Sebastien Deleuze
 */
suspend fun DatabaseClient.GenericExecuteSpec.await() {
	then().awaitFirstOrNull()
}

/**
 * Extension for [DatabaseClient.BindSpec.bind] providing a variant leveraging reified type parameters
 *
 * @author Mark Paluch
 * @author Ibanga Enoobong Ime
 */
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T : Any> DatabaseClient.GenericExecuteSpec.bind(index: Int, value: T?) = bind(index, Parameter.fromOrEmpty(value, T::class.java))

/**
 * Extension for [DatabaseClient.BindSpec.bind] providing a variant leveraging reified type parameters
 *
 * @author Mark Paluch
 * @author Ibanga Enoobong Ime
 */
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T : Any> DatabaseClient.GenericExecuteSpec.bind(name: String, value: T?) = bind(name, Parameter.fromOrEmpty(value, T::class.java))
