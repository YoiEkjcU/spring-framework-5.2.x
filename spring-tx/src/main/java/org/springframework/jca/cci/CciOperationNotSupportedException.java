package org.springframework.jca.cci;

import javax.resource.ResourceException;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

/**
 * Exception thrown when the connector doesn't support a specific CCI operation.
 *
 * @author Juergen Hoeller
 * @since 1.2
 * @deprecated as of 5.3, in favor of specific data access APIs
 * (or native CCI usage if there is no alternative)
 */
@Deprecated
@SuppressWarnings("serial")
public class CciOperationNotSupportedException extends InvalidDataAccessResourceUsageException {

	/**
	 * Constructor for CciOperationNotSupportedException.
	 *
	 * @param msg message
	 * @param ex  the root ResourceException cause
	 */
	public CciOperationNotSupportedException(String msg, ResourceException ex) {
		super(msg, ex);
	}

}
