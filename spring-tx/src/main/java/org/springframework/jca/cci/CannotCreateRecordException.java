package org.springframework.jca.cci;

import javax.resource.ResourceException;

import org.springframework.dao.DataAccessResourceFailureException;

/**
 * Exception thrown when the creating of a CCI Record failed
 * for connector-internal reasons.
 *
 * @author Juergen Hoeller
 * @since 1.2
 * @deprecated as of 5.3, in favor of specific data access APIs
 * (or native CCI usage if there is no alternative)
 */
@Deprecated
@SuppressWarnings("serial")
public class CannotCreateRecordException extends DataAccessResourceFailureException {

	/**
	 * Constructor for CannotCreateRecordException.
	 *
	 * @param msg message
	 * @param ex  the root ResourceException cause
	 */
	public CannotCreateRecordException(String msg, ResourceException ex) {
		super(msg, ex);
	}

}
