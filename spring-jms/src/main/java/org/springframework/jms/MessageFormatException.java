package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS MessageFormatException.
 *
 * @author Mark Pollack
 * @see javax.jms.MessageFormatException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class MessageFormatException extends JmsException {

	public MessageFormatException(javax.jms.MessageFormatException cause) {
		super(cause);
	}

}
