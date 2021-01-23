package org.springframework.r2dbc.core

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

/**
 * Unit tests for [UpdatedRowsFetchSpec] extensions.
 *
 * @author Fred Montariol
 */
class UpdatedRowsFetchSpecExtensionsTests {

	@Test
	fun awaitRowsUpdatedWithValue() {
		val spec = mockk<UpdatedRowsFetchSpec>()
		every { spec.rowsUpdated() } returns Mono.just(42)

		runBlocking {
			assertThat(spec.awaitRowsUpdated()).isEqualTo(42)
		}

		verify {
			spec.rowsUpdated()
		}
	}

}
