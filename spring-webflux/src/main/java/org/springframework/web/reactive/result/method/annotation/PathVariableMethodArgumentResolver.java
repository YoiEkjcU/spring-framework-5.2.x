package org.springframework.web.reactive.result.method.annotation;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebExchange;

/**
 * Resolves method arguments annotated with @{@link PathVariable}.
 *
 * <p>An @{@link PathVariable} is a named value that gets resolved from a URI
 * template variable. It is always required and does not have a default value
 * to fall back on. See the base class
 * {@link org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver}
 * for more information on how named values are processed.
 *
 * <p>If the method parameter type is {@link Map}, the name specified in the
 * annotation is used to resolve the URI variable String value. The value is
 * then converted to a {@link Map} via type conversion, assuming a suitable
 * {@link Converter}.
 *
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @see PathVariableMapMethodArgumentResolver
 * @since 5.0
 */
public class PathVariableMethodArgumentResolver extends AbstractNamedValueSyncArgumentResolver {

	/**
	 * Create a new {@link PathVariableMethodArgumentResolver}.
	 *
	 * @param factory  a bean factory to use for resolving {@code ${...}}
	 *                 placeholder and {@code #{...}} SpEL expressions in default values;
	 *                 or {@code null} if default values are not expected to contain expressions
	 * @param registry for checking reactive type wrappers
	 */
	public PathVariableMethodArgumentResolver(@Nullable ConfigurableBeanFactory factory,
											  ReactiveAdapterRegistry registry) {

		super(factory, registry);
	}


	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return checkAnnotatedParamNoReactiveWrapper(parameter, PathVariable.class, this::singlePathVariable);
	}

	private boolean singlePathVariable(PathVariable pathVariable, Class<?> type) {
		return !Map.class.isAssignableFrom(type) || StringUtils.hasText(pathVariable.name());
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		PathVariable ann = parameter.getParameterAnnotation(PathVariable.class);
		Assert.state(ann != null, "No PathVariable annotation");
		return new PathVariableNamedValueInfo(ann);
	}

	@Override
	protected Object resolveNamedValue(String name, MethodParameter parameter, ServerWebExchange exchange) {
		String attributeName = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
		return exchange.getAttributeOrDefault(attributeName, Collections.emptyMap()).get(name);
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter) {
		throw new ServerErrorException(name, parameter, null);
	}

	@Override
	protected void handleResolvedValue(
			@Nullable Object arg, String name, MethodParameter parameter, Model model, ServerWebExchange exchange) {

		// TODO: View.PATH_VARIABLES ?
	}


	private static class PathVariableNamedValueInfo extends NamedValueInfo {

		public PathVariableNamedValueInfo(PathVariable annotation) {
			super(annotation.name(), annotation.required(), ValueConstants.DEFAULT_NONE);
		}
	}

}
