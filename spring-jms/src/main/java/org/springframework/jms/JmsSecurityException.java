package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS JMSSecurityException.
 *
 * @author Mark Pollack
 * @see javax.jms.JMSSecurityException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class JmsSecurityException extends JmsException {

	public JmsSecurityException(javax.jms.JMSSecurityException cause) {
		super(cause);
	}

}
