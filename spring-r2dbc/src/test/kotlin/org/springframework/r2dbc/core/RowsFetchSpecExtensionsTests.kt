package org.springframework.r2dbc.core

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.springframework.dao.EmptyResultDataAccessException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Unit tests for [RowsFetchSpec] extensions.
 *
 * @author Sebastien Deleuze
 * @author Mark Paluch
 */
class RowsFetchSpecExtensionsTests {

	@Test
	fun awaitOneWithValue() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.one() } returns Mono.just("foo")

		runBlocking {
			assertThat(spec.awaitOne()).isEqualTo("foo")
		}

		verify {
			spec.one()
		}
	}

	@Test
	fun awaitOneWithNull() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.one() } returns Mono.empty()

		assertThatExceptionOfType(EmptyResultDataAccessException::class.java).isThrownBy {
			runBlocking { spec.awaitOne() }
		}

		verify {
			spec.one()
		}
	}

	@Test
	fun awaitOneOrNullWithValue() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.one() } returns Mono.just("foo")

		runBlocking {
			assertThat(spec.awaitOneOrNull()).isEqualTo("foo")
		}

		verify {
			spec.one()
		}
	}

	@Test
	fun awaitOneOrNullWithNull() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.one() } returns Mono.empty()

		runBlocking {
			assertThat(spec.awaitOneOrNull()).isNull()
		}

		verify {
			spec.one()
		}
	}

	@Test
	fun awaitFirstWithValue() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.first() } returns Mono.just("foo")

		runBlocking {
			assertThat(spec.awaitFirst()).isEqualTo("foo")
		}

		verify {
			spec.first()
		}
	}

	@Test
	fun awaitFirstWithNull() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.first() } returns Mono.empty()

		assertThatExceptionOfType(EmptyResultDataAccessException::class.java).isThrownBy {
			runBlocking { spec.awaitFirst() }
		}

		verify {
			spec.first()
		}
	}

	@Test
	fun awaitFirstOrNullWithValue() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.first() } returns Mono.just("foo")

		runBlocking {
			assertThat(spec.awaitFirstOrNull()).isEqualTo("foo")
		}

		verify {
			spec.first()
		}
	}

	@Test
	fun awaitFirstOrNullWithNull() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.first() } returns Mono.empty()

		runBlocking {
			assertThat(spec.awaitFirstOrNull()).isNull()
		}

		verify {
			spec.first()
		}
	}

	@Test
	@ExperimentalCoroutinesApi
	fun allAsFlow() {
		val spec = mockk<RowsFetchSpec<String>>()
		every { spec.all() } returns Flux.just("foo", "bar", "baz")

		runBlocking {
			assertThat(spec.flow().toList()).contains("foo", "bar", "baz")
		}

		verify {
			spec.all()
		}
	}

}
