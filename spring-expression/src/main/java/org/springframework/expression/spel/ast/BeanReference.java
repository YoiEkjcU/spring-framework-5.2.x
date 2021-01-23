package org.springframework.expression.spel.ast;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelMessage;

/**
 * Represents a bean reference to a type, for example <tt>@foo</tt> or <tt>@'foo.bar'</tt>.
 * For a FactoryBean the syntax <tt>&foo</tt> can be used to access the factory itself.
 *
 * @author Andy Clement
 */
public class BeanReference extends SpelNodeImpl {

	private static final String FACTORY_BEAN_PREFIX = "&";

	private final String beanName;


	public BeanReference(int startPos, int endPos, String beanName) {
		super(startPos, endPos);
		this.beanName = beanName;
	}


	@Override
	public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
		BeanResolver beanResolver = state.getEvaluationContext().getBeanResolver();
		if (beanResolver == null) {
			throw new SpelEvaluationException(
					getStartPosition(), SpelMessage.NO_BEAN_RESOLVER_REGISTERED, this.beanName);
		}

		try {
			return new TypedValue(beanResolver.resolve(state.getEvaluationContext(), this.beanName));
		} catch (AccessException ex) {
			throw new SpelEvaluationException(getStartPosition(), ex, SpelMessage.EXCEPTION_DURING_BEAN_RESOLUTION,
					this.beanName, ex.getMessage());
		}
	}

	@Override
	public String toStringAST() {
		StringBuilder sb = new StringBuilder();
		if (!this.beanName.startsWith(FACTORY_BEAN_PREFIX)) {
			sb.append("@");
		}
		if (!this.beanName.contains(".")) {
			sb.append(this.beanName);
		} else {
			sb.append("'").append(this.beanName).append("'");
		}
		return sb.toString();
	}

}
