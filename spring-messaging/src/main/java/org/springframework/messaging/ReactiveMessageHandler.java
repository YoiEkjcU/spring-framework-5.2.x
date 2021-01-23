package org.springframework.messaging;

import reactor.core.publisher.Mono;

/**
 * Reactive contract for handling a {@link Message}.
 *
 * @author Rossen Stoyanchev
 * @see MessageHandler
 * @since 5.2
 */
@FunctionalInterface
public interface ReactiveMessageHandler {

	/**
	 * Handle the given message.
	 *
	 * @param message the message to be handled
	 * @return a completion {@link Mono} for the result of the message handling
	 */
	Mono<Void> handleMessage(Message<?> message);

}
