package org.springframework.web.reactive.result.view.freemarker;

import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

/**
 * A {@code ViewResolver} for resolving {@link FreeMarkerView} instances, i.e.
 * FreeMarker templates and custom subclasses of it.
 *
 * <p>The view class for all views generated by this resolver can be specified
 * via the "viewClass" property. See {@link UrlBasedViewResolver} for details.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
public class FreeMarkerViewResolver extends UrlBasedViewResolver {

	/**
	 * Simple constructor.
	 */
	public FreeMarkerViewResolver() {
		setViewClass(requiredViewClass());
	}

	/**
	 * Convenience constructor with a prefix and suffix.
	 *
	 * @param suffix the suffix to prepend view names with
	 * @param prefix the prefix to prepend view names with
	 */
	public FreeMarkerViewResolver(String prefix, String suffix) {
		setViewClass(requiredViewClass());
		setPrefix(prefix);
		setSuffix(suffix);
	}


	/**
	 * Requires {@link FreeMarkerView}.
	 */
	@Override
	protected Class<?> requiredViewClass() {
		return FreeMarkerView.class;
	}

}
