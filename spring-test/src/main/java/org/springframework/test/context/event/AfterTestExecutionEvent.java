package org.springframework.test.context.event;

import org.springframework.test.context.TestContext;

/**
 * {@link TestContextEvent} published by the {@link EventPublishingTestExecutionListener} when
 * {@link org.springframework.test.context.TestExecutionListener#afterTestExecution(TestContext)}
 * is invoked.
 *
 * @author Frank Scheffler
 * @see org.springframework.test.context.event.annotation.AfterTestExecution @AfterTestExecution
 * @since 5.2
 */
@SuppressWarnings("serial")
public class AfterTestExecutionEvent extends TestContextEvent {

	public AfterTestExecutionEvent(TestContext source) {
		super(source);
	}

}
