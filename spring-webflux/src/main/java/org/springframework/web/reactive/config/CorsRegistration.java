package org.springframework.web.reactive.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.cors.CorsConfiguration;

/**
 * Assists with the creation of a {@link CorsConfiguration} instance for a given
 * URL path pattern.
 *
 * @author Sebastien Deleuze
 * @author Rossen Stoyanchev
 * @see CorsConfiguration
 * @see CorsRegistry
 * @since 5.0
 */
public class CorsRegistration {

	private final String pathPattern;

	private final CorsConfiguration config;


	public CorsRegistration(String pathPattern) {
		this.pathPattern = pathPattern;
		// Same implicit default values as the @CrossOrigin annotation + allows simple methods
		this.config = new CorsConfiguration().applyPermitDefaultValues();
	}


	/**
	 * A list of origins for which cross-origin requests are allowed. Please,
	 * see {@link CorsConfiguration#setAllowedOrigins(List)} for details.
	 * <p>By default all origins are allowed unless {@code originPatterns} is
	 * also set in which case {@code originPatterns} is used instead.
	 */
	public CorsRegistration allowedOrigins(String... origins) {
		this.config.setAllowedOrigins(Arrays.asList(origins));
		return this;
	}

	/**
	 * Alternative to {@link #allowCredentials} that supports origins declared
	 * via wildcard patterns. Please, see
	 *
	 * @link CorsConfiguration#setAllowedOriginPatterns(List)} for details.
	 * <p>By default this is not set.
	 * @since 5.3
	 */
	public CorsRegistration allowedOriginPatterns(String... patterns) {
		this.config.setAllowedOriginPatterns(Arrays.asList(patterns));
		return this;
	}

	/**
	 * Set the HTTP methods to allow, e.g. {@code "GET"}, {@code "POST"}, etc.
	 * <p>The special value {@code "*"} allows all methods.
	 * <p>By default "simple" methods {@code GET}, {@code HEAD}, and {@code POST}
	 * are allowed.
	 */
	public CorsRegistration allowedMethods(String... methods) {
		this.config.setAllowedMethods(Arrays.asList(methods));
		return this;
	}

	/**
	 * Set the list of headers that a pre-flight request can list as allowed
	 * for use during an actual request.
	 * <p>The special value {@code "*"} may be used to allow all headers.
	 * <p>A header name is not required to be listed if it is one of:
	 * {@code Cache-Control}, {@code Content-Language}, {@code Expires},
	 * {@code Last-Modified}, or {@code Pragma} as per the CORS spec.
	 * <p>By default all headers are allowed.
	 */
	public CorsRegistration allowedHeaders(String... headers) {
		this.config.setAllowedHeaders(Arrays.asList(headers));
		return this;
	}

	/**
	 * Set the list of response headers other than "simple" headers, i.e.
	 * {@code Cache-Control}, {@code Content-Language}, {@code Content-Type},
	 * {@code Expires}, {@code Last-Modified}, or {@code Pragma}, that an
	 * actual response might have and can be exposed.
	 * <p>Note that {@code "*"} is not supported on this property.
	 * <p>By default this is not set.
	 */
	public CorsRegistration exposedHeaders(String... headers) {
		this.config.setExposedHeaders(Arrays.asList(headers));
		return this;
	}

	/**
	 * Whether the browser should send credentials, such as cookies along with
	 * cross domain requests, to the annotated endpoint. The configured value is
	 * set on the {@code Access-Control-Allow-Credentials} response header of
	 * preflight requests.
	 * <p><strong>NOTE:</strong> Be aware that this option establishes a high
	 * level of trust with the configured domains and also increases the surface
	 * attack of the web application by exposing sensitive user-specific
	 * information such as cookies and CSRF tokens.
	 * <p>By default this is not set in which case the
	 * {@code Access-Control-Allow-Credentials} header is also not set and
	 * credentials are therefore not allowed.
	 */
	public CorsRegistration allowCredentials(boolean allowCredentials) {
		this.config.setAllowCredentials(allowCredentials);
		return this;
	}

	/**
	 * Configure how long in seconds the response from a pre-flight request
	 * can be cached by clients.
	 * <p>By default this is set to 1800 seconds (30 minutes).
	 */
	public CorsRegistration maxAge(long maxAge) {
		this.config.setMaxAge(maxAge);
		return this;
	}

	protected String getPathPattern() {
		return this.pathPattern;
	}

	protected CorsConfiguration getCorsConfiguration() {
		return this.config;
	}

}
