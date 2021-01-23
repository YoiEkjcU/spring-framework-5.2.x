package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS TransactionInProgressException.
 *
 * @author Mark Pollack
 * @see javax.jms.TransactionInProgressException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class TransactionInProgressException extends JmsException {

	public TransactionInProgressException(javax.jms.TransactionInProgressException cause) {
		super(cause);
	}

}
