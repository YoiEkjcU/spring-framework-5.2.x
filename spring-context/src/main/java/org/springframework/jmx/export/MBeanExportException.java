package org.springframework.jmx.export;

import org.springframework.jmx.JmxException;

/**
 * Exception thrown in case of failure when exporting an MBean.
 *
 * @author Rob Harrop
 * @see MBeanExportOperations
 * @since 2.0
 */
@SuppressWarnings("serial")
public class MBeanExportException extends JmxException {

	/**
	 * Create a new {@code MBeanExportException} with the
	 * specified error message.
	 *
	 * @param msg the detail message
	 */
	public MBeanExportException(String msg) {
		super(msg);
	}

	/**
	 * Create a new {@code MBeanExportException} with the
	 * specified error message and root cause.
	 *
	 * @param msg   the detail message
	 * @param cause the root cause
	 */
	public MBeanExportException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
