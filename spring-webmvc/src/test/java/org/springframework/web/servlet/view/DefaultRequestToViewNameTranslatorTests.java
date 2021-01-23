package org.springframework.web.servlet.view;

import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.web.servlet.handler.PathPatternsParameterizedTest;
import org.springframework.web.servlet.handler.PathPatternsTestUtils;
import org.springframework.web.testfixture.servlet.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rick Evans
 * @author Juergen Hoeller
 */
public class DefaultRequestToViewNameTranslatorTests {

	private static final String VIEW_NAME = "apple";
	private static final String EXTENSION = ".html";

	private final DefaultRequestToViewNameTranslator translator = new DefaultRequestToViewNameTranslator();


	@SuppressWarnings("unused")
	private static Stream<Function<String, MockHttpServletRequest>> pathPatternsArguments() {
		return PathPatternsTestUtils.requestArguments("/sundays");
	}


	@PathPatternsParameterizedTest
	void testGetViewNameLeavesLeadingSlashIfSoConfigured(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME + "/");
		this.translator.setStripLeadingSlash(false);
		assertViewName(request, "/" + VIEW_NAME);
	}

	@PathPatternsParameterizedTest
	void testGetViewNameLeavesTrailingSlashIfSoConfigured(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME + "/");
		this.translator.setStripTrailingSlash(false);
		assertViewName(request, VIEW_NAME + "/");
	}

	@PathPatternsParameterizedTest
	void testGetViewNameLeavesExtensionIfSoConfigured(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME + EXTENSION);
		this.translator.setStripExtension(false);
		assertViewName(request, VIEW_NAME + EXTENSION);
	}

	@PathPatternsParameterizedTest
	void testGetViewNameWithDefaultConfiguration(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME + EXTENSION);
		assertViewName(request, VIEW_NAME);
	}

	@PathPatternsParameterizedTest
	void testGetViewNameWithCustomSeparator(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME + "/fiona" + EXTENSION);
		this.translator.setSeparator("_");
		assertViewName(request, VIEW_NAME + "_fiona");
	}

	@PathPatternsParameterizedTest
	void testGetViewNameWithNoExtension(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME);
		assertViewName(request, VIEW_NAME);
	}

	@PathPatternsParameterizedTest
	void testGetViewNameWithSemicolonContent(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME + ";a=A;b=B");
		assertViewName(request, VIEW_NAME);
	}

	@PathPatternsParameterizedTest
	void testGetViewNameWithPrefix(Function<String, MockHttpServletRequest> requestFactory) {
		final String prefix = "fiona_";
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME);
		this.translator.setPrefix(prefix);
		assertViewName(request, prefix + VIEW_NAME);
	}

	@PathPatternsParameterizedTest
	void testGetViewNameWithNullPrefix(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME);
		this.translator.setPrefix(null);
		assertViewName(request, VIEW_NAME);
	}

	@PathPatternsParameterizedTest
	void testGetViewNameWithSuffix(Function<String, MockHttpServletRequest> requestFactory) {
		final String suffix = ".fiona";
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME);
		this.translator.setSuffix(suffix);
		assertViewName(request, VIEW_NAME + suffix);
	}

	@PathPatternsParameterizedTest
	void testGetViewNameWithNullSuffix(Function<String, MockHttpServletRequest> requestFactory) {
		MockHttpServletRequest request = requestFactory.apply(VIEW_NAME);
		this.translator.setSuffix(null);
		assertViewName(request, VIEW_NAME);
	}


	private void assertViewName(MockHttpServletRequest request, String expectedViewName) {
		String actualViewName = this.translator.getViewName(request);
		assertThat(actualViewName).isNotNull();
		assertThat(actualViewName)
				.as("Did not get the expected viewName from the DefaultRequestToViewNameTranslator.getViewName(..)")
				.isEqualTo(expectedViewName);
	}

}
