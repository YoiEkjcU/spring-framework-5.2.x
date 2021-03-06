package org.springframework.remoting;

/**
 * RemoteAccessException subclass to be thrown in case of a failure
 * within the client-side proxy for a remote service, for example
 * when a method was not found on the underlying RMI stub.
 *
 * @author Juergen Hoeller
 * @see RemoteInvocationFailureException
 * @since 1.2.8
 */
@SuppressWarnings("serial")
public class RemoteProxyFailureException extends RemoteAccessException {

	/**
	 * Constructor for RemoteProxyFailureException.
	 *
	 * @param msg   the detail message
	 * @param cause the root cause from the remoting API in use
	 */
	public RemoteProxyFailureException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
