package org.springframework.web.servlet.mvc.condition;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A contract for {@code "name!=value"} style expression used to specify request
 * parameters and request header conditions in {@code @RequestMapping}.
 *
 * @param <T> the value type
 * @author Rossen Stoyanchev
 * @see RequestMapping#params()
 * @see RequestMapping#headers()
 * @since 3.1
 */
public interface NameValueExpression<T> {

	String getName();

	@Nullable
	T getValue();

	boolean isNegated();

}
