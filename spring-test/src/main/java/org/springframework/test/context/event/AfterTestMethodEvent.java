package org.springframework.test.context.event;

import org.springframework.test.context.TestContext;

/**
 * {@link TestContextEvent} published by the {@link EventPublishingTestExecutionListener} when
 * {@link org.springframework.test.context.TestExecutionListener#afterTestMethod(TestContext)}
 * is invoked.
 *
 * @author Frank Scheffler
 * @see org.springframework.test.context.event.annotation.AfterTestMethod @AfterTestMethod
 * @since 5.2
 */
@SuppressWarnings("serial")
public class AfterTestMethodEvent extends TestContextEvent {

	public AfterTestMethodEvent(TestContext source) {
		super(source);
	}

}
