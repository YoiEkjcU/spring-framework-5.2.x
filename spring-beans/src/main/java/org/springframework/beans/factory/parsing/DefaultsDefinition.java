package org.springframework.beans.factory.parsing;

import org.springframework.beans.BeanMetadataElement;

/**
 * Marker interface for a defaults definition,
 * extending BeanMetadataElement to inherit source exposure.
 *
 * <p>Concrete implementations are typically based on 'document defaults',
 * for example specified at the root tag level within an XML document.
 *
 * @author Juergen Hoeller
 * @see org.springframework.beans.factory.xml.DocumentDefaultsDefinition
 * @see ReaderEventListener#defaultsRegistered(DefaultsDefinition)
 * @since 2.0.2
 */
public interface DefaultsDefinition extends BeanMetadataElement {

}
