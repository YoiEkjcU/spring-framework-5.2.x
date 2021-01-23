package org.springframework.web.filter.reactive;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.testfixture.http.server.reactive.MockServerHttpRequest;
import org.springframework.web.testfixture.http.server.reactive.MockServerHttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ServerWebExchangeContextFilter}.
 * @author Rossen Stoyanchev
 */
class ServerWebExchangeContextFilterTests {

	@Test
	void extractServerWebExchangeFromContext() {
		MyService service = new MyService();

		HttpHandler httpHandler = WebHttpHandlerBuilder
				.webHandler(exchange -> service.service().then())
				.filter(new ServerWebExchangeContextFilter())
				.build();

		httpHandler.handle(MockServerHttpRequest.get("/path").build(), new MockServerHttpResponse())
				.block(Duration.ofSeconds(5));

		assertThat(service.getExchange()).isNotNull();
	}


	private static class MyService {

		private final AtomicReference<ServerWebExchange> exchangeRef = new AtomicReference<>();


		public ServerWebExchange getExchange() {
			return this.exchangeRef.get();
		}

		public Mono<String> service() {
			return Mono.just("result").contextWrite(context -> {
				ServerWebExchangeContextFilter.get(context).ifPresent(exchangeRef::set);
				return context;
			});
		}
	}

}
