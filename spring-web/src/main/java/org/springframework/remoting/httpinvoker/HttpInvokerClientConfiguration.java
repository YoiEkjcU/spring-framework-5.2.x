package org.springframework.remoting.httpinvoker;

import org.springframework.lang.Nullable;

/**
 * Configuration interface for executing HTTP invoker requests.
 *
 * @author Juergen Hoeller
 * @see HttpInvokerRequestExecutor
 * @see HttpInvokerClientInterceptor
 * @since 1.1
 * @deprecated as of 5.3 (phasing out serialization-based remoting)
 */
@Deprecated
public interface HttpInvokerClientConfiguration {

	/**
	 * Return the HTTP URL of the target service.
	 */
	String getServiceUrl();

	/**
	 * Return the codebase URL to download classes from if not found locally.
	 * Can consist of multiple URLs, separated by spaces.
	 *
	 * @return the codebase URL, or {@code null} if none
	 * @see java.rmi.server.RMIClassLoader
	 */
	@Nullable
	String getCodebaseUrl();

}
