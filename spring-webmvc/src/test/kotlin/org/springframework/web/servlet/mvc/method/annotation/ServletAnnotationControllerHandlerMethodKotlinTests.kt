package org.springframework.web.servlet.mvc.method.annotation

import org.assertj.core.api.Assertions.assertThat
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.handler.PathPatternsParameterizedTest
import org.springframework.web.testfixture.servlet.MockHttpServletRequest
import org.springframework.web.testfixture.servlet.MockHttpServletResponse
import java.util.stream.Stream

/**
 * @author Sebastien Deleuze
 */
class ServletAnnotationControllerHandlerMethodKotlinTests : AbstractServletHandlerMethodTests() {

	companion object {
		@JvmStatic
		fun pathPatternsArguments(): Stream<Boolean> {
			return Stream.of(true, false)
		}
	}

	@PathPatternsParameterizedTest
	fun dataClassBinding(usePathPatterns: Boolean) {
		initDispatcherServlet(DataClassController::class.java, usePathPatterns)

		val request = MockHttpServletRequest("GET", "/bind")
		request.addParameter("param1", "value1")
		request.addParameter("param2", "2")
		val response = MockHttpServletResponse()
		servlet.service(request, response)
		assertThat(response.contentAsString).isEqualTo("value1-2")
	}

	@PathPatternsParameterizedTest
	fun dataClassBindingWithOptionalParameterAndAllParameters(usePathPatterns: Boolean) {
		initDispatcherServlet(DataClassController::class.java, usePathPatterns)

		val request = MockHttpServletRequest("GET", "/bind-optional-parameter")
		request.addParameter("param1", "value1")
		request.addParameter("param2", "2")
		val response = MockHttpServletResponse()
		servlet.service(request, response)
		assertThat(response.contentAsString).isEqualTo("value1-2")
	}

	@PathPatternsParameterizedTest
	fun dataClassBindingWithOptionalParameterAndOnlyMissingParameters(usePathPatterns: Boolean) {
		initDispatcherServlet(DataClassController::class.java, usePathPatterns)

		val request = MockHttpServletRequest("GET", "/bind-optional-parameter")
		request.addParameter("param1", "value1")
		val response = MockHttpServletResponse()
		servlet.service(request, response)
		assertThat(response.contentAsString).isEqualTo("value1-12")
	}


	data class DataClass(val param1: String, val param2: Int)

	data class DataClassWithOptionalParameter(val param1: String, val param2: Int = 12)

	@RestController
	class DataClassController {

		@RequestMapping("/bind")
		fun handle(data: DataClass) = "${data.param1}-${data.param2}"

		@RequestMapping("/bind-optional-parameter")
		fun handle(data: DataClassWithOptionalParameter) = "${data.param1}-${data.param2}"
	}

}
