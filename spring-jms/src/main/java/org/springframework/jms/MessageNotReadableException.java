package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS MessageNotReadableException.
 *
 * @author Mark Pollack
 * @see javax.jms.MessageNotReadableException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class MessageNotReadableException extends JmsException {

	public MessageNotReadableException(javax.jms.MessageNotReadableException cause) {
		super(cause);
	}

}
