package org.springframework.jca.cci.connection;

import javax.resource.cci.Connection;

import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * Resource holder wrapping a CCI {@link Connection}.
 * {@link CciLocalTransactionManager} binds instances of this class to the thread,
 * for a given {@link javax.resource.cci.ConnectionFactory}.
 *
 * <p>Note: This is an SPI class, not intended to be used by applications.
 *
 * @author Thierry Templier
 * @author Juergen Hoeller
 * @see CciLocalTransactionManager
 * @see ConnectionFactoryUtils
 * @since 1.2
 * @deprecated as of 5.3, in favor of specific data access APIs
 * (or native CCI usage if there is no alternative)
 */
@Deprecated
public class ConnectionHolder extends ResourceHolderSupport {

	private final Connection connection;


	public ConnectionHolder(Connection connection) {
		this.connection = connection;
	}


	public Connection getConnection() {
		return this.connection;
	}

}
