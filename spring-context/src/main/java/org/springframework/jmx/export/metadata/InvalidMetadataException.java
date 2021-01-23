package org.springframework.jmx.export.metadata;

import org.springframework.jmx.JmxException;

/**
 * Thrown by the {@code JmxAttributeSource} when it encounters
 * incorrect metadata on a managed resource or one of its methods.
 *
 * @author Rob Harrop
 * @see JmxAttributeSource
 * @see org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler
 * @since 1.2
 */
@SuppressWarnings("serial")
public class InvalidMetadataException extends JmxException {

	/**
	 * Create a new {@code InvalidMetadataException} with the supplied
	 * error message.
	 *
	 * @param msg the detail message
	 */
	public InvalidMetadataException(String msg) {
		super(msg);
	}

}
