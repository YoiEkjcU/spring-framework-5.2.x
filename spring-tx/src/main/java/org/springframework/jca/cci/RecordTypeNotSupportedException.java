package org.springframework.jca.cci;

import javax.resource.ResourceException;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

/**
 * Exception thrown when the creating of a CCI Record failed because
 * the connector doesn't support the desired CCI Record type.
 *
 * @author Juergen Hoeller
 * @since 1.2
 * @deprecated as of 5.3, in favor of specific data access APIs
 * (or native CCI usage if there is no alternative)
 */
@Deprecated
@SuppressWarnings("serial")
public class RecordTypeNotSupportedException extends InvalidDataAccessResourceUsageException {

	/**
	 * Constructor for RecordTypeNotSupportedException.
	 *
	 * @param msg message
	 * @param ex  the root ResourceException cause
	 */
	public RecordTypeNotSupportedException(String msg, ResourceException ex) {
		super(msg, ex);
	}

}
