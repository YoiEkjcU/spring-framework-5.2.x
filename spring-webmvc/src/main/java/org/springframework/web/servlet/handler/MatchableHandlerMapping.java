package org.springframework.web.servlet.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * Additional interface that a {@link HandlerMapping} can implement to expose
 * a request matching API aligned with its internal request matching
 * configuration and implementation.
 *
 * @author Rossen Stoyanchev
 * @see HandlerMappingIntrospector
 * @since 4.3.1
 */
public interface MatchableHandlerMapping extends HandlerMapping {

	/**
	 * Return the parser of this {@code HandlerMapping}, if configured in which
	 * case pre-parsed patterns are used.
	 *
	 * @since 5.3
	 */
	@Nullable
	default PathPatternParser getPatternParser() {
		return null;
	}

	/**
	 * Determine whether the request matches the given pattern. Use this method
	 * when {@link #getPatternParser()} returns {@code null} which means that the
	 * {@code HandlerMapping} is uses String pattern matching.
	 *
	 * @param request the current request
	 * @param pattern the pattern to match
	 * @return the result from request matching, or {@code null} if none
	 */
	@Nullable
	RequestMatchResult match(HttpServletRequest request, String pattern);

}
