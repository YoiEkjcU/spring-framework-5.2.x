package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS IllegalStateException.
 *
 * @author Mark Pollack
 * @see javax.jms.IllegalStateException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class IllegalStateException extends JmsException {

	public IllegalStateException(javax.jms.IllegalStateException cause) {
		super(cause);
	}

}
