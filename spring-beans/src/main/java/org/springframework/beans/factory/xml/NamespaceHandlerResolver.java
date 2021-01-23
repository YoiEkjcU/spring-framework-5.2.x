package org.springframework.beans.factory.xml;

import org.springframework.lang.Nullable;

/**
 * Used by the {@link org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader} to
 * locate a {@link NamespaceHandler} implementation for a particular namespace URI.
 *
 * @author Rob Harrop
 * @see NamespaceHandler
 * @see org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader
 * @since 2.0
 */
@FunctionalInterface
public interface NamespaceHandlerResolver {

	/**
	 * Resolve the namespace URI and return the located {@link NamespaceHandler}
	 * implementation.
	 *
	 * @param namespaceUri the relevant namespace URI
	 * @return the located {@link NamespaceHandler} (may be {@code null})
	 */
	@Nullable
	NamespaceHandler resolve(String namespaceUri);

}
