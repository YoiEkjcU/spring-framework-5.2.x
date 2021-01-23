package org.springframework.beans.factory.parsing;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

/**
 * Simple strategy allowing tools to control how source metadata is attached
 * to the bean definition metadata.
 *
 * <p>Configuration parsers <strong>may</strong> provide the ability to attach
 * source metadata during the parse phase. They will offer this metadata in a
 * generic format which can be further modified by a {@link SourceExtractor}
 * before being attached to the bean definition metadata.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @see org.springframework.beans.BeanMetadataElement#getSource()
 * @see org.springframework.beans.factory.config.BeanDefinition
 * @since 2.0
 */
@FunctionalInterface
public interface SourceExtractor {

	/**
	 * Extract the source metadata from the candidate object supplied
	 * by the configuration parser.
	 *
	 * @param sourceCandidate  the original source metadata (never {@code null})
	 * @param definingResource the resource that defines the given source object
	 *                         (may be {@code null})
	 * @return the source metadata object to store (may be {@code null})
	 */
	@Nullable
	Object extractSource(Object sourceCandidate, @Nullable Resource definingResource);

}
