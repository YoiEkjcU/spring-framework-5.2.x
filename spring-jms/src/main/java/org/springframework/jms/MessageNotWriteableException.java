package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS MessageNotWriteableException.
 *
 * @author Mark Pollack
 * @see javax.jms.MessageNotWriteableException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class MessageNotWriteableException extends JmsException {

	public MessageNotWriteableException(javax.jms.MessageNotWriteableException cause) {
		super(cause);
	}

}
