package org.springframework.web.accept;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Extends {@code PathExtensionContentNegotiationStrategy} that also uses
 * {@link ServletContext#getMimeType(String)} to resolve file extensions.
 *
 * @author Rossen Stoyanchev
 * @since 3.2
 * @deprecated as of 5.2.4. See class-level note in
 * {@link ContentNegotiationManagerFactoryBean} on the deprecation of path
 * extension config options.
 */
@Deprecated
public class ServletPathExtensionContentNegotiationStrategy extends PathExtensionContentNegotiationStrategy {

	private final ServletContext servletContext;


	/**
	 * Create an instance without any mappings to start with. Mappings may be
	 * added later when extensions are resolved through
	 * {@link ServletContext#getMimeType(String)} or via
	 * {@link org.springframework.http.MediaTypeFactory}.
	 */
	public ServletPathExtensionContentNegotiationStrategy(ServletContext context) {
		this(context, null);
	}

	/**
	 * Create an instance with the given extension-to-MediaType lookup.
	 */
	public ServletPathExtensionContentNegotiationStrategy(
			ServletContext servletContext, @Nullable Map<String, MediaType> mediaTypes) {

		super(mediaTypes);
		Assert.notNull(servletContext, "ServletContext is required");
		this.servletContext = servletContext;
	}


	/**
	 * Resolve file extension via {@link ServletContext#getMimeType(String)}
	 * and also delegate to base class for a potential
	 * {@link org.springframework.http.MediaTypeFactory} lookup.
	 */
	@Override
	@Nullable
	protected MediaType handleNoMatch(NativeWebRequest webRequest, String extension)
			throws HttpMediaTypeNotAcceptableException {

		MediaType mediaType = null;
		String mimeType = this.servletContext.getMimeType("file." + extension);
		if (StringUtils.hasText(mimeType)) {
			mediaType = MediaType.parseMediaType(mimeType);
		}
		if (mediaType == null || MediaType.APPLICATION_OCTET_STREAM.equals(mediaType)) {
			MediaType superMediaType = super.handleNoMatch(webRequest, extension);
			if (superMediaType != null) {
				mediaType = superMediaType;
			}
		}
		return mediaType;
	}

	/**
	 * Extends the base class
	 * {@link PathExtensionContentNegotiationStrategy#getMediaTypeForResource}
	 * with the ability to also look up through the ServletContext.
	 *
	 * @param resource the resource to look up
	 * @return the MediaType for the extension, or {@code null} if none found
	 * @since 4.3
	 */
	@Override
	public MediaType getMediaTypeForResource(Resource resource) {
		MediaType mediaType = null;
		String mimeType = this.servletContext.getMimeType(resource.getFilename());
		if (StringUtils.hasText(mimeType)) {
			mediaType = MediaType.parseMediaType(mimeType);
		}
		if (mediaType == null || MediaType.APPLICATION_OCTET_STREAM.equals(mediaType)) {
			MediaType superMediaType = super.getMediaTypeForResource(resource);
			if (superMediaType != null) {
				mediaType = superMediaType;
			}
		}
		return mediaType;
	}

}
