package org.springframework.context.event;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

/**
 * Utility class for handling SpEL expression parsing for application events.
 * <p>Meant to be used as a reusable, thread-safe component.
 *
 * @author Stephane Nicoll
 * @see CachedExpressionEvaluator
 * @since 4.2
 */
class EventExpressionEvaluator extends CachedExpressionEvaluator {

	private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);


	/**
	 * Determine if the condition defined by the specified expression evaluates
	 * to {@code true}.
	 */
	public boolean condition(String conditionExpression, ApplicationEvent event, Method targetMethod,
							 AnnotatedElementKey methodKey, Object[] args, @Nullable BeanFactory beanFactory) {

		EventExpressionRootObject root = new EventExpressionRootObject(event, args);
		MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(
				root, targetMethod, args, getParameterNameDiscoverer());
		if (beanFactory != null) {
			evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
		}

		return (Boolean.TRUE.equals(getExpression(this.conditionCache, methodKey, conditionExpression).getValue(
				evaluationContext, Boolean.class)));
	}

}
