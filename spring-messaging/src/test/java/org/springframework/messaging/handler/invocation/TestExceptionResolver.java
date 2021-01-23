package org.springframework.messaging.handler.invocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.MethodIntrospector;
import org.springframework.util.ReflectionUtils;

/**
 * Sub-class for {@link AbstractExceptionHandlerMethodResolver} for testing.
 * @author Rossen Stoyanchev
 */
public class TestExceptionResolver extends AbstractExceptionHandlerMethodResolver {

	private final static ReflectionUtils.MethodFilter EXCEPTION_HANDLER_METHOD_FILTER =
			method -> method.getName().matches("handle[\\w]*Exception");


	public TestExceptionResolver(Class<?> handlerType) {
		super(initExceptionMappings(handlerType));
	}

	private static Map<Class<? extends Throwable>, Method> initExceptionMappings(Class<?> handlerType) {
		Map<Class<? extends Throwable>, Method> result = new HashMap<>();
		for (Method method : MethodIntrospector.selectMethods(handlerType, EXCEPTION_HANDLER_METHOD_FILTER)) {
			for (Class<? extends Throwable> exception : getExceptionsFromMethodSignature(method)) {
				result.put(exception, method);
			}
		}
		return result;
	}

}
