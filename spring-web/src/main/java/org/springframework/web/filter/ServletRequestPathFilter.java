package org.springframework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.ServletRequestPathUtils;

/**
 * A {@code Filter} to {@link ServletRequestPathUtils#parseAndCache parse}
 * and cache a {@link org.springframework.http.server.RequestPath} for further
 * {@link ServletRequestPathUtils#getParsedRequestPath access} throughout the
 * filter chain. This is useful when parsed
 * {@link org.springframework.web.util.pattern.PathPattern}s are in use anywhere
 * in an application instead of String pattern matching with
 * {@link org.springframework.util.PathMatcher}.
 * <p>Note that in Spring MVC, the {@code DispatcherServlet} will also parse and
 * cache the {@code RequestPath} if it detects that parsed {@code PathPatterns}
 * are enabled for any {@code HandlerMapping} but it will skip doing that if it
 * finds the {@link ServletRequestPathUtils#PATH_ATTRIBUTE} already exists.
 *
 * @author Rossen Stoyanchev
 * @since 5.3
 */
public class ServletRequestPathFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ServletRequestPathUtils.parseAndCache((HttpServletRequest) request);
		try {
			chain.doFilter(request, response);
		} finally {
			ServletRequestPathUtils.clearParsedRequestPath(request);
		}
	}

}
