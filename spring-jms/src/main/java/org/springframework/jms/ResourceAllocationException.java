package org.springframework.jms;

/**
 * Runtime exception mirroring the JMS ResourceAllocationException.
 *
 * @author Mark Pollack
 * @see javax.jms.ResourceAllocationException
 * @since 1.1
 */
@SuppressWarnings("serial")
public class ResourceAllocationException extends JmsException {

	public ResourceAllocationException(javax.jms.ResourceAllocationException cause) {
		super(cause);
	}

}
