package org.springframework.jdbc.support.xml;

import org.springframework.jdbc.support.SqlValue;

/**
 * Subinterface of {@link org.springframework.jdbc.support.SqlValue}
 * that supports passing in XML data to specified column and adds a
 * cleanup callback, to be invoked after the value has been set and
 * the corresponding statement has been executed.
 *
 * @author Thomas Risberg
 * @see org.springframework.jdbc.support.SqlValue
 * @since 2.5.5
 */
public interface SqlXmlValue extends SqlValue {

}
