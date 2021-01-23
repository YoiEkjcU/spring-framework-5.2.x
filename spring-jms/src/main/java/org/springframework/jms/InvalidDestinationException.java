package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS InvalidDestinationException.
 *
 * @author Mark Pollack
 * @see javax.jms.InvalidDestinationException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class InvalidDestinationException extends JmsException {

	public InvalidDestinationException(javax.jms.InvalidDestinationException cause) {
		super(cause);
	}

}
