package org.springframework.aop.config;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Adrian Colyer
 * @author Chris Beams
 */
public class AopNamespaceHandlerAdviceTypeTests {

	@Test
	public void testParsingOfAdviceTypes() {
		new ClassPathXmlApplicationContext(getClass().getSimpleName() + "-ok.xml", getClass());
	}

	@Test
	public void testParsingOfAdviceTypesWithError() {
		assertThatExceptionOfType(BeanDefinitionStoreException.class).isThrownBy(() ->
				new ClassPathXmlApplicationContext(getClass().getSimpleName() + "-error.xml", getClass()))
			.matches(ex -> ex.contains(SAXParseException.class));
	}

}
