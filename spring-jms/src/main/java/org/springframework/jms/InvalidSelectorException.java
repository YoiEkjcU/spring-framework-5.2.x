package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS InvalidSelectorException.
 *
 * @author Mark Pollack
 * @see javax.jms.InvalidSelectorException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class InvalidSelectorException extends JmsException {

	public InvalidSelectorException(javax.jms.InvalidSelectorException cause) {
		super(cause);
	}

}
