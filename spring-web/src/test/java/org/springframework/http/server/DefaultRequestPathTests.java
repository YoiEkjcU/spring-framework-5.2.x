package org.springframework.http.server;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DefaultRequestPath}.
 * @author Rossen Stoyanchev
 */
class DefaultRequestPathTests {

	@Test
	void requestPath() {
		// basic
		testRequestPath("/app/a/b/c", "/app", "/a/b/c");

		// no context path
		testRequestPath("/a/b/c", "", "/a/b/c");

		// context path only
		testRequestPath("/a/b", "/a/b", "");

		// root path
		testRequestPath("/", "", "/");

		// empty path
		testRequestPath("", "", "");
		testRequestPath("", "/", "");

		// trailing slash
		testRequestPath("/app/a/", "/app", "/a/");
		testRequestPath("/app/a//", "/app", "/a//");
	}

	private void testRequestPath(String fullPath, String contextPath, String pathWithinApplication) {

		RequestPath requestPath = RequestPath.parse(fullPath, contextPath);

		Object expected = contextPath.equals("/") ? "" : contextPath;
		assertThat(requestPath.contextPath().value()).isEqualTo(expected);
		assertThat(requestPath.pathWithinApplication().value()).isEqualTo(pathWithinApplication);
	}

	@Test
	void updateRequestPath() {

		RequestPath requestPath = RequestPath.parse("/aA/bB/cC", null);

		assertThat(requestPath.contextPath().value()).isEqualTo("");
		assertThat(requestPath.pathWithinApplication().value()).isEqualTo("/aA/bB/cC");

		requestPath = requestPath.modifyContextPath("/aA");

		assertThat(requestPath.contextPath().value()).isEqualTo("/aA");
		assertThat(requestPath.pathWithinApplication().value()).isEqualTo("/bB/cC");
	}

}
