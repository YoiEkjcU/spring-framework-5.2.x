package org.springframework.test.web.servlet.htmlunit;

import com.gargoylesoftware.htmlunit.WebRequest;

/**
 * Strategy for matching on a {@link WebRequest}.
 *
 * @author Rob Winch
 * @see org.springframework.test.web.servlet.htmlunit.HostRequestMatcher
 * @see org.springframework.test.web.servlet.htmlunit.UrlRegexRequestMatcher
 * @since 4.2
 */
@FunctionalInterface
public interface WebRequestMatcher {

	/**
	 * Whether this matcher matches on the supplied web request.
	 *
	 * @param request the {@link WebRequest} to attempt to match on
	 * @return {@code true} if this matcher matches on the {@code WebRequest}
	 */
	boolean matches(WebRequest request);

}
