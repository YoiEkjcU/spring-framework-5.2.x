package org.springframework.remoting.soap;

import javax.xml.namespace.QName;

import org.springframework.remoting.RemoteInvocationFailureException;

/**
 * RemoteInvocationFailureException subclass that provides the details
 * of a SOAP fault.
 *
 * @author Juergen Hoeller
 * @see javax.xml.rpc.soap.SOAPFaultException
 * @see javax.xml.ws.soap.SOAPFaultException
 * @since 2.5
 */
@SuppressWarnings("serial")
public abstract class SoapFaultException extends RemoteInvocationFailureException {

	/**
	 * Constructor for SoapFaultException.
	 *
	 * @param msg   the detail message
	 * @param cause the root cause from the SOAP API in use
	 */
	protected SoapFaultException(String msg, Throwable cause) {
		super(msg, cause);
	}


	/**
	 * Return the SOAP fault code.
	 */
	public abstract String getFaultCode();

	/**
	 * Return the SOAP fault code as a {@code QName} object.
	 */
	public abstract QName getFaultCodeAsQName();

	/**
	 * Return the descriptive SOAP fault string.
	 */
	public abstract String getFaultString();

	/**
	 * Return the actor that caused this fault.
	 */
	public abstract String getFaultActor();

}
