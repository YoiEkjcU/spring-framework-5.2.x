package org.springframework.test.context.event;

import org.springframework.test.context.TestContext;

/**
 * {@link TestContextEvent} published by the {@link EventPublishingTestExecutionListener} when
 * {@link org.springframework.test.context.TestExecutionListener#prepareTestInstance(TestContext)}
 * is invoked.
 *
 * @author Frank Scheffler
 * @see org.springframework.test.context.event.annotation.PrepareTestInstance @PrepareTestInstance
 * @since 5.2
 */
@SuppressWarnings("serial")
public class PrepareTestInstanceEvent extends TestContextEvent {

	public PrepareTestInstanceEvent(TestContext source) {
		super(source);
	}

}
