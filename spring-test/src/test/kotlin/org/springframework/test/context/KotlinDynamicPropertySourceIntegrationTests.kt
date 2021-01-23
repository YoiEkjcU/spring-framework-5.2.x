package org.springframework.test.context

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Component
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

/**
 * Kotlin integration test for [@DynamicPropertySource][DynamicPropertySource].
 *
 * @author Sebastien Deleuze
 * @author Phillip Webb
 * @author Sam Brannen
 */
@SpringJUnitConfig
class KotlinDynamicPropertySourceIntegrationTests {

	@Test
	fun hasInjectedValues(@Autowired service: Service) {
		Assertions.assertThat(service.ip).isEqualTo("127.0.0.1")
		Assertions.assertThat(service.port).isEqualTo(4242)
	}

	@Configuration
	@Import(Service::class)
	open class Config

	@Component
	class Service(@Value("\${test.container.ip}") val ip: String, @Value("\${test.container.port}") val port: Int)

	class DemoContainer(val ipAddress: String = "127.0.0.1", val port: Int = 4242)

	companion object {

		@JvmStatic
		val container = DemoContainer()

		@DynamicPropertySource
		@JvmStatic
		fun containerProperties(registry: DynamicPropertyRegistry) {
			registry.add("test.container.ip") { container.ipAddress }
			registry.add("test.container.port") { container.port }
		}
	}
}
