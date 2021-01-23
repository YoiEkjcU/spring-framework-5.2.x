package org.springframework.web.socket.sockjs.client;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.springframework.web.socket.sockjs.transport.TransportType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@code SockJsUrlInfo}.
 *
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 */
class SockJsUrlInfoTests {

	@Test
	void serverId() throws Exception {
		SockJsUrlInfo info = new SockJsUrlInfo(new URI("https://example.com"));
		int serverId = Integer.parseInt(info.getServerId());
		assertThat(serverId).isGreaterThanOrEqualTo(0).isLessThan(1000);
	}

	@Test
	void sessionId() throws Exception {
		SockJsUrlInfo info = new SockJsUrlInfo(new URI("https://example.com"));
		assertThat(info.getSessionId()).as("Invalid sessionId: " + info.getSessionId()).hasSize(32);
	}

	@ParameterizedTest
	@CsvSource( {
		"http, http",
		"https, https",
		"ws, http",
		"wss, https",
	})
	void infoUrl(String scheme, String expectedScheme) throws Exception {
		SockJsUrlInfo info = new SockJsUrlInfo(new URI(scheme + "://example.com"));
		assertThat(info.getInfoUrl()).isEqualTo(new URI(expectedScheme + "://example.com/info"));
	}

	@ParameterizedTest
	@CsvSource( {
		"http, http, XHR_STREAMING",
		"http, ws, WEBSOCKET",
		"https, https, XHR_STREAMING",
		"https, wss, WEBSOCKET",
		"ws, http, XHR_STREAMING",
		"ws, ws, WEBSOCKET",
		"wss, https, XHR_STREAMING",
		"wss, wss, WEBSOCKET"
	})
	void transportUrl(String scheme, String expectedScheme, TransportType transportType) throws Exception {
		SockJsUrlInfo info = new SockJsUrlInfo(new URI(scheme + "://example.com"));
		String serverId = info.getServerId();
		String sessionId = info.getSessionId();
		String transport = transportType.toString().toLowerCase();
		URI expected = new URI(expectedScheme + "://example.com/" + serverId + "/" + sessionId + "/" + transport);
		assertThat(info.getTransportUrl(transportType)).isEqualTo(expected);
	}

}
