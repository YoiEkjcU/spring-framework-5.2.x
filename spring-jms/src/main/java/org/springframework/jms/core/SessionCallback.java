package org.springframework.jms.core;

import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.lang.Nullable;

/**
 * Callback for executing any number of operations on a provided {@link Session}.
 *
 * <p>To be used with the {@link JmsTemplate#execute(SessionCallback)} method,
 * often implemented as an anonymous inner class or as a lambda expression.
 *
 * @param <T> the result type
 * @author Mark Pollack
 * @see JmsTemplate#execute(SessionCallback)
 * @since 1.1
 */
@FunctionalInterface
public interface SessionCallback<T> {

	/**
	 * Execute any number of operations against the supplied JMS {@link Session},
	 * possibly returning a result.
	 *
	 * @param session the JMS {@code Session}
	 * @return a result object from working with the {@code Session}, if any
	 * (or {@code null} if none)
	 * @throws javax.jms.JMSException if thrown by JMS API methods
	 */
	@Nullable
	T doInJms(Session session) throws JMSException;

}
