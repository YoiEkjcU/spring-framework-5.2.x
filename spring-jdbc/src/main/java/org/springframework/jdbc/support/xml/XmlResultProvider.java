package org.springframework.jdbc.support.xml;

import javax.xml.transform.Result;

/**
 * Interface defining handling involved with providing {@code Result}
 * data for XML input.
 *
 * @author Thomas Risberg
 * @see javax.xml.transform.Result
 * @since 2.5.5
 */
public interface XmlResultProvider {

	/**
	 * Implementations must implement this method to provide the XML content
	 * for the {@code Result}. Implementations will vary depending on
	 * the {@code Result} implementation used.
	 *
	 * @param result the {@code Result} object being used to provide the XML input
	 */
	void provideXml(Result result);

}
