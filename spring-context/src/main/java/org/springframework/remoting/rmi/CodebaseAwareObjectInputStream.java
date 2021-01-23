package org.springframework.remoting.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.RMIClassLoader;

import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.lang.Nullable;

/**
 * Special ObjectInputStream subclass that falls back to a specified codebase
 * to load classes from if not found locally. In contrast to standard RMI
 * conventions for dynamic class download, it is the client that determines
 * the codebase URL here, rather than the "java.rmi.server.codebase" system
 * property on the server.
 *
 * <p>Uses the JDK's RMIClassLoader to load classes from the specified codebase.
 * The codebase can consist of multiple URLs, separated by spaces.
 * Note that RMIClassLoader requires a SecurityManager to be set, like when
 * using dynamic class download with standard RMI! (See the RMI documentation
 * for details.)
 *
 * <p>Despite residing in the RMI package, this class is <i>not</i> used for
 * RmiClientInterceptor, which uses the standard RMI infrastructure instead
 * and thus is only able to rely on RMI's standard dynamic class download via
 * "java.rmi.server.codebase". CodebaseAwareObjectInputStream is used by
 * HttpInvokerClientInterceptor (see the "codebaseUrl" property there).
 *
 * <p>Thanks to Lionel Mestre for suggesting the option and providing
 * a prototype!
 *
 * @author Juergen Hoeller
 * @see java.rmi.server.RMIClassLoader
 * @see RemoteInvocationSerializingExporter#createObjectInputStream
 * @see org.springframework.remoting.httpinvoker.HttpInvokerClientInterceptor#setCodebaseUrl
 * @since 1.1.3
 * @deprecated as of 5.3 (phasing out serialization-based remoting)
 */
@Deprecated
public class CodebaseAwareObjectInputStream extends ConfigurableObjectInputStream {

	private final String codebaseUrl;


	/**
	 * Create a new CodebaseAwareObjectInputStream for the given InputStream and codebase.
	 *
	 * @param in          the InputStream to read from
	 * @param codebaseUrl the codebase URL to load classes from if not found locally
	 *                    (can consist of multiple URLs, separated by spaces)
	 * @see java.io.ObjectInputStream#ObjectInputStream(java.io.InputStream)
	 */
	public CodebaseAwareObjectInputStream(InputStream in, String codebaseUrl) throws IOException {
		this(in, null, codebaseUrl);
	}

	/**
	 * Create a new CodebaseAwareObjectInputStream for the given InputStream and codebase.
	 *
	 * @param in          the InputStream to read from
	 * @param classLoader the ClassLoader to use for loading local classes
	 *                    (may be {@code null} to indicate RMI's default ClassLoader)
	 * @param codebaseUrl the codebase URL to load classes from if not found locally
	 *                    (can consist of multiple URLs, separated by spaces)
	 * @see java.io.ObjectInputStream#ObjectInputStream(java.io.InputStream)
	 */
	public CodebaseAwareObjectInputStream(
			InputStream in, @Nullable ClassLoader classLoader, String codebaseUrl) throws IOException {

		super(in, classLoader);
		this.codebaseUrl = codebaseUrl;
	}

	/**
	 * Create a new CodebaseAwareObjectInputStream for the given InputStream and codebase.
	 *
	 * @param in                 the InputStream to read from
	 * @param classLoader        the ClassLoader to use for loading local classes
	 *                           (may be {@code null} to indicate RMI's default ClassLoader)
	 * @param acceptProxyClasses whether to accept deserialization of proxy classes
	 *                           (may be deactivated as a security measure)
	 * @see java.io.ObjectInputStream#ObjectInputStream(java.io.InputStream)
	 */
	public CodebaseAwareObjectInputStream(
			InputStream in, @Nullable ClassLoader classLoader, boolean acceptProxyClasses) throws IOException {

		super(in, classLoader, acceptProxyClasses);
		this.codebaseUrl = null;
	}


	@Override
	protected Class<?> resolveFallbackIfPossible(String className, ClassNotFoundException ex)
			throws IOException, ClassNotFoundException {

		// If codebaseUrl is set, try to load the class with the RMIClassLoader.
		// Else, propagate the ClassNotFoundException.
		if (this.codebaseUrl == null) {
			throw ex;
		}
		return RMIClassLoader.loadClass(this.codebaseUrl, className);
	}

	@Override
	protected ClassLoader getFallbackClassLoader() throws IOException {
		return RMIClassLoader.getClassLoader(this.codebaseUrl);
	}

}
