package org.springframework.web.servlet.function;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.web.servlet.handler.PathPatternsTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author Arjen Poutsma
 */
public class RouterFunctionsTests {

	private final ServerRequest request = new DefaultServerRequest(
			PathPatternsTestUtils.initRequest("GET", "", true), Collections.emptyList());


	@Test
	public void routeMatch() {
		HandlerFunction<ServerResponse> handlerFunction = request -> ServerResponse.ok().build();

		RequestPredicate requestPredicate = mock(RequestPredicate.class);
		given(requestPredicate.test(request)).willReturn(true);

		RouterFunction<ServerResponse>
				result = RouterFunctions.route(requestPredicate, handlerFunction);
		assertThat(result).isNotNull();

		Optional<HandlerFunction<ServerResponse>> resultHandlerFunction = result.route(request);
		assertThat(resultHandlerFunction.isPresent()).isTrue();
		assertThat(resultHandlerFunction.get()).isEqualTo(handlerFunction);
	}

	@Test
	public void routeNoMatch() {
		HandlerFunction<ServerResponse> handlerFunction = request -> ServerResponse.ok().build();

		RequestPredicate requestPredicate = mock(RequestPredicate.class);
		given(requestPredicate.test(request)).willReturn(false);

		RouterFunction<ServerResponse> result = RouterFunctions.route(requestPredicate, handlerFunction);
		assertThat(result).isNotNull();

		Optional<HandlerFunction<ServerResponse>> resultHandlerFunction = result.route(request);
		assertThat(resultHandlerFunction.isPresent()).isFalse();
	}

	@Test
	public void nestMatch() {
		HandlerFunction<ServerResponse> handlerFunction = request -> ServerResponse.ok().build();
		RouterFunction<ServerResponse> routerFunction = request -> Optional.of(handlerFunction);

		RequestPredicate requestPredicate = mock(RequestPredicate.class);
		given(requestPredicate.nest(request)).willReturn(Optional.of(request));

		RouterFunction<ServerResponse> result = RouterFunctions.nest(requestPredicate, routerFunction);
		assertThat(result).isNotNull();

		Optional<HandlerFunction<ServerResponse>> resultHandlerFunction = result.route(request);
		assertThat(resultHandlerFunction.isPresent()).isTrue();
		assertThat(resultHandlerFunction.get()).isEqualTo(handlerFunction);
	}

	@Test
	public void nestNoMatch() {
		HandlerFunction<ServerResponse> handlerFunction = request -> ServerResponse.ok().build();
		RouterFunction<ServerResponse> routerFunction = request -> Optional.of(handlerFunction);

		RequestPredicate requestPredicate = mock(RequestPredicate.class);
		given(requestPredicate.nest(request)).willReturn(Optional.empty());

		RouterFunction<ServerResponse> result = RouterFunctions.nest(requestPredicate, routerFunction);
		assertThat(result).isNotNull();

		Optional<HandlerFunction<ServerResponse>> resultHandlerFunction = result.route(request);
		assertThat(resultHandlerFunction.isPresent()).isFalse();
	}

}
