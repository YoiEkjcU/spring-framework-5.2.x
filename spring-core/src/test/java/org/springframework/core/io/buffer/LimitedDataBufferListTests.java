package org.springframework.core.io.buffer;

import java.nio.charset.StandardCharsets;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link LimitedDataBufferList}.
 * @author Rossen Stoyanchev
 * @since 5.1.11
 */
public class LimitedDataBufferListTests {

	@Test
	void limitEnforced() {
		Assertions.assertThatThrownBy(() -> new LimitedDataBufferList(5).add(toDataBuffer("123456")))
				.isInstanceOf(DataBufferLimitException.class);
	}

	@Test
	void limitIgnored() {
		new LimitedDataBufferList(-1).add(toDataBuffer("123456"));
	}

	@Test
	void clearResetsCount() {
		LimitedDataBufferList list = new LimitedDataBufferList(5);
		list.add(toDataBuffer("12345"));
		list.clear();
		list.add(toDataBuffer("12345"));
	}


	private static DataBuffer toDataBuffer(String value) {
		byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
		return DefaultDataBufferFactory.sharedInstance.wrap(bytes);
	}

}
