package org.springframework.r2dbc.core

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

/**
 * Unit tests for [DatabaseClient] extensions.
 *
 * @author Sebastien Deleuze
 * @author Jonas Bark
 * @author Mark Paluch
 */
class DatabaseClientExtensionsTests {

	@Test
	fun bindByIndexShouldBindValue() {
		val spec = mockk<DatabaseClient.GenericExecuteSpec>()
		every { spec.bind(eq(0), any()) } returns spec

		runBlocking {
			spec.bind<String>(0, "foo")
		}

		verify {
			spec.bind(0, Parameter.fromOrEmpty("foo", String::class.java))
		}
	}

	@Test
	fun bindByIndexShouldBindNull() {
		val spec = mockk<DatabaseClient.GenericExecuteSpec>()
		every { spec.bind(eq(0), any()) } returns spec

		runBlocking {
			spec.bind<String>(0, null)
		}

		verify {
			spec.bind(0, Parameter.empty(String::class.java))
		}
	}

	@Test
	fun bindByNameShouldBindValue() {
		val spec = mockk<DatabaseClient.GenericExecuteSpec>()
		every { spec.bind(eq("field"), any()) } returns spec

		runBlocking {
			spec.bind<String>("field", "foo")
		}

		verify {
			spec.bind("field", Parameter.fromOrEmpty("foo", String::class.java))
		}
	}

	@Test
	fun bindByNameShouldBindNull() {
		val spec = mockk<DatabaseClient.GenericExecuteSpec>()
		every { spec.bind(eq("field"), any()) } returns spec

		runBlocking {
			spec.bind<String>("field", null)
		}

		verify {
			spec.bind("field", Parameter.empty(String::class.java))
		}
	}

	@Test
	fun genericExecuteSpecAwait() {
		val spec = mockk<DatabaseClient.GenericExecuteSpec>()
		every { spec.then() } returns Mono.empty()

		runBlocking {
			spec.await()
		}

		verify {
			spec.then()
		}
	}

}
