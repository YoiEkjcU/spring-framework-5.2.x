package org.springframework.jms.config;

import org.springframework.jms.listener.MessageListenerContainer;

/**
 * Factory of {@link MessageListenerContainer} based on a
 * {@link JmsListenerEndpoint} definition.
 *
 * @param <C> the container type
 * @author Stephane Nicoll
 * @see JmsListenerEndpoint
 * @since 4.1
 */
public interface JmsListenerContainerFactory<C extends MessageListenerContainer> {

	/**
	 * Create a {@link MessageListenerContainer} for the given {@link JmsListenerEndpoint}.
	 *
	 * @param endpoint the endpoint to configure
	 * @return the created container
	 */
	C createListenerContainer(JmsListenerEndpoint endpoint);

}
