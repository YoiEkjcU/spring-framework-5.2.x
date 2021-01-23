package org.springframework.jca.cci;

import javax.resource.ResourceException;

import org.springframework.dao.DataAccessResourceFailureException;

/**
 * Fatal exception thrown when we can't connect to an EIS using CCI.
 *
 * @author Thierry Templier
 * @author Juergen Hoeller
 * @since 1.2
 * @deprecated as of 5.3, in favor of specific data access APIs
 * (or native CCI usage if there is no alternative)
 */
@Deprecated
@SuppressWarnings("serial")
public class CannotGetCciConnectionException extends DataAccessResourceFailureException {

	/**
	 * Constructor for CannotGetCciConnectionException.
	 *
	 * @param msg message
	 * @param ex  the root ResourceException cause
	 */
	public CannotGetCciConnectionException(String msg, ResourceException ex) {
		super(msg, ex);
	}

}
