package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS TransactionRolledBackException.
 *
 * @author Mark Pollack
 * @see javax.jms.TransactionRolledBackException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class TransactionRolledBackException extends JmsException {

	public TransactionRolledBackException(javax.jms.TransactionRolledBackException cause) {
		super(cause);
	}

}
