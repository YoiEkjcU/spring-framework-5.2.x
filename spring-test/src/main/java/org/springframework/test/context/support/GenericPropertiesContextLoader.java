package org.springframework.test.context.support;

import java.util.Properties;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.ObjectUtils;

/**
 * Concrete implementation of {@link AbstractGenericContextLoader} that reads
 * bean definitions from Java {@link Properties} resources.
 *
 * @author Sam Brannen
 * @since 2.5
 * @deprecated as of 5.3, in favor of Spring's common bean definition formats
 * and/or custom loader implementations
 */
@Deprecated
public class GenericPropertiesContextLoader extends AbstractGenericContextLoader {

	/**
	 * Creates a new {@link org.springframework.beans.factory.support.PropertiesBeanDefinitionReader}.
	 *
	 * @return a new PropertiesBeanDefinitionReader
	 * @see org.springframework.beans.factory.support.PropertiesBeanDefinitionReader
	 */
	@Override
	protected BeanDefinitionReader createBeanDefinitionReader(final GenericApplicationContext context) {
		return new org.springframework.beans.factory.support.PropertiesBeanDefinitionReader(context);
	}

	/**
	 * Returns &quot;{@code -context.properties}&quot;.
	 */
	@Override
	protected String getResourceSuffix() {
		return "-context.properties";
	}

	/**
	 * Ensure that the supplied {@link MergedContextConfiguration} does not
	 * contain {@link MergedContextConfiguration#getClasses() classes}.
	 *
	 * @see AbstractGenericContextLoader#validateMergedContextConfiguration
	 * @since 4.0.4
	 */
	@Override
	protected void validateMergedContextConfiguration(MergedContextConfiguration mergedConfig) {
		if (mergedConfig.hasClasses()) {
			String msg = String.format(
					"Test class [%s] has been configured with @ContextConfiguration's 'classes' attribute %s, "
							+ "but %s does not support annotated classes.", mergedConfig.getTestClass().getName(),
					ObjectUtils.nullSafeToString(mergedConfig.getClasses()), getClass().getSimpleName());
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
	}

}
