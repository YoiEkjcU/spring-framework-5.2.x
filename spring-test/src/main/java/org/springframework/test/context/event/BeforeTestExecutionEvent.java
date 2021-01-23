package org.springframework.test.context.event;

import org.springframework.test.context.TestContext;

/**
 * {@link TestContextEvent} published by the {@link EventPublishingTestExecutionListener} when
 * {@link org.springframework.test.context.TestExecutionListener#beforeTestExecution(TestContext)}
 * is invoked.
 *
 * @author Frank Scheffler
 * @see org.springframework.test.context.event.annotation.BeforeTestExecution @BeforeTestExecution
 * @since 5.2
 */
@SuppressWarnings("serial")
public class BeforeTestExecutionEvent extends TestContextEvent {

	public BeforeTestExecutionEvent(TestContext source) {
		super(source);
	}

}
