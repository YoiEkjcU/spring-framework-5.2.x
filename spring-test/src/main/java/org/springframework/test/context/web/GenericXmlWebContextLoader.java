package org.springframework.test.context.web;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * Concrete implementation of {@link AbstractGenericWebContextLoader} that loads
 * bean definitions from XML resources.
 *
 * <p>Default resource locations are detected using the suffix
 * {@code "-context.xml"}.
 *
 * @author Sam Brannen
 * @see GenericGroovyXmlWebContextLoader
 * @see AnnotationConfigWebContextLoader
 * @since 3.2
 */
public class GenericXmlWebContextLoader extends AbstractGenericWebContextLoader {

	/**
	 * Load bean definitions into the supplied {@link GenericWebApplicationContext context}
	 * from the locations in the supplied {@code WebMergedContextConfiguration}, using an
	 * {@link XmlBeanDefinitionReader}.
	 *
	 * @see AbstractGenericWebContextLoader#loadBeanDefinitions
	 */
	@Override
	protected void loadBeanDefinitions(GenericWebApplicationContext context,
									   WebMergedContextConfiguration webMergedConfig) {
		new XmlBeanDefinitionReader(context).loadBeanDefinitions(webMergedConfig.getLocations());
	}

	/**
	 * Returns {@code "-context.xml"} in order to support detection of a
	 * default XML config file.
	 */
	@Override
	protected String getResourceSuffix() {
		return "-context.xml";
	}

	/**
	 * Ensure that the supplied {@link WebMergedContextConfiguration} does not
	 * contain {@link MergedContextConfiguration#getClasses() classes}.
	 *
	 * @see AbstractGenericWebContextLoader#validateMergedContextConfiguration
	 * @since 4.0.4
	 */
	@Override
	protected void validateMergedContextConfiguration(WebMergedContextConfiguration webMergedConfig) {
		if (webMergedConfig.hasClasses()) {
			String msg = String.format(
					"Test class [%s] has been configured with @ContextConfiguration's 'classes' attribute %s, "
							+ "but %s does not support annotated classes.", webMergedConfig.getTestClass().getName(),
					ObjectUtils.nullSafeToString(webMergedConfig.getClasses()), getClass().getSimpleName());
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
	}

}
