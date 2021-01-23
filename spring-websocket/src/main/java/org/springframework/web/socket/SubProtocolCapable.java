package org.springframework.web.socket;

import java.util.List;

/**
 * An interface for WebSocket handlers that support sub-protocols as defined in RFC 6455.
 *
 * @author Rossen Stoyanchev
 * @see WebSocketHandler
 * @see <a href="https://tools.ietf.org/html/rfc6455#section-1.9">RFC-6455 section 1.9</a>
 * @since 4.0
 */
public interface SubProtocolCapable {

	/**
	 * Return the list of supported sub-protocols.
	 */
	List<String> getSubProtocols();

}
