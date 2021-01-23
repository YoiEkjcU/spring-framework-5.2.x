package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS MessageEOFException.
 *
 * @author Mark Pollack
 * @see javax.jms.MessageEOFException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class MessageEOFException extends JmsException {

	public MessageEOFException(javax.jms.MessageEOFException cause) {
		super(cause);
	}

}
