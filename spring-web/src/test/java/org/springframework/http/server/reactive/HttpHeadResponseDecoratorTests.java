package org.springframework.http.server.reactive;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.PooledByteBufAllocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.core.testfixture.io.buffer.LeakAwareDataBufferFactory;
import org.springframework.web.testfixture.http.server.reactive.MockServerHttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link HttpHeadResponseDecorator}.
 * @author Rossen Stoyanchev
 */
public class HttpHeadResponseDecoratorTests {

	private final LeakAwareDataBufferFactory bufferFactory =
			new LeakAwareDataBufferFactory(new NettyDataBufferFactory(PooledByteBufAllocator.DEFAULT));

	private final ServerHttpResponse response =
			new HttpHeadResponseDecorator(new MockServerHttpResponse(this.bufferFactory));


	@AfterEach
	public void tearDown() {
		this.bufferFactory.checkForLeaks();
	}


	@Test
	public void write() {
		Flux<DataBuffer> body = Flux.just(toDataBuffer("data1"), toDataBuffer("data2"));
		this.response.writeWith(body).block();
		assertThat(this.response.getHeaders().getContentLength()).isEqualTo(10);
	}

	@Test // gh-23484
	public void writeWithGivenContentLength() {
		int length = 15;
		this.response.getHeaders().setContentLength(length);
		this.response.writeWith(Flux.empty()).block();
		assertThat(this.response.getHeaders().getContentLength()).isEqualTo(length);
	}


	private DataBuffer toDataBuffer(String s) {
		DataBuffer buffer = this.bufferFactory.allocateBuffer();
		buffer.write(s.getBytes(StandardCharsets.UTF_8));
		return buffer;
	}

}
