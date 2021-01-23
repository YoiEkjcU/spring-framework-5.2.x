package org.springframework.web.reactive.function.server;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.testfixture.http.server.reactive.bootstrap.AbstractHttpHandlerIntegrationTests;

/**
 * @author Arjen Poutsma
 */
public abstract class AbstractRouterFunctionIntegrationTests extends AbstractHttpHandlerIntegrationTests {

	@Override
	protected final HttpHandler createHttpHandler() {
		RouterFunction<?> routerFunction = routerFunction();
		return RouterFunctions.toHttpHandler(routerFunction, handlerStrategies());
	}

	protected abstract RouterFunction<?> routerFunction();

	protected HandlerStrategies handlerStrategies() {
		return HandlerStrategies.withDefaults();
	}

}
