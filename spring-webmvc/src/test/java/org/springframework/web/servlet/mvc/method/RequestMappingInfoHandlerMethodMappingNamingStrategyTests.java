package org.springframework.web.servlet.mvc.method;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerMethodMappingNamingStrategy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link RequestMappingInfoHandlerMethodMappingNamingStrategy}.
 *
 * @author Rossen Stoyanchev
 */
public class RequestMappingInfoHandlerMethodMappingNamingStrategyTests {

	@Test
	public void getNameExplicit() {
		Method method = ClassUtils.getMethod(TestController.class, "handle");
		HandlerMethod handlerMethod = new HandlerMethod(new TestController(), method);

		@SuppressWarnings("deprecation")
		RequestMappingInfo rmi = new RequestMappingInfo("foo", null, null, null, null, null, null, null);

		HandlerMethodMappingNamingStrategy<RequestMappingInfo> strategy = new RequestMappingInfoHandlerMethodMappingNamingStrategy();

		assertThat(strategy.getName(handlerMethod, rmi)).isEqualTo("foo");
	}

	@Test
	public void getNameConvention() {
		Method method = ClassUtils.getMethod(TestController.class, "handle");
		HandlerMethod handlerMethod = new HandlerMethod(new TestController(), method);

		@SuppressWarnings("deprecation")
		RequestMappingInfo rmi = new RequestMappingInfo(null, null, null, null, null, null, null, null);

		HandlerMethodMappingNamingStrategy<RequestMappingInfo> strategy = new RequestMappingInfoHandlerMethodMappingNamingStrategy();

		assertThat(strategy.getName(handlerMethod, rmi)).isEqualTo("TC#handle");
	}


	private static class TestController {

		@RequestMapping
		public void handle() {
		}
	}

}
