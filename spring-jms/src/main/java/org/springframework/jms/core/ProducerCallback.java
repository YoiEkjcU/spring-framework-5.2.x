package org.springframework.jms.core;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.lang.Nullable;

/**
 * Callback for sending a message to a JMS destination.
 *
 * <p>To be used with {@link JmsTemplate}'s callback methods that take a
 * {@link ProducerCallback} argument, often implemented as an anonymous
 * inner class or as a lambda expression.
 *
 * <p>The typical implementation will perform multiple operations on the
 * supplied JMS {@link Session} and {@link MessageProducer}.
 *
 * @param <T> the result type
 * @author Mark Pollack
 * @see JmsTemplate#execute(ProducerCallback)
 * @see JmsTemplate#execute(javax.jms.Destination, ProducerCallback)
 * @see JmsTemplate#execute(String, ProducerCallback)
 * @since 1.1
 */
@FunctionalInterface
public interface ProducerCallback<T> {

	/**
	 * Perform operations on the given {@link Session} and {@link MessageProducer}.
	 * <p>The message producer is not associated with any destination unless
	 * when specified in the JmsTemplate call.
	 *
	 * @param session  the JMS {@code Session} object to use
	 * @param producer the JMS {@code MessageProducer} object to use
	 * @return a result object from working with the {@code Session}, if any
	 * (or {@code null} if none)
	 * @throws javax.jms.JMSException if thrown by JMS API methods
	 */
	@Nullable
	T doInJms(Session session, MessageProducer producer) throws JMSException;

}
