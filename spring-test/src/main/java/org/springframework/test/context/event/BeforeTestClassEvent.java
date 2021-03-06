package org.springframework.test.context.event;

import org.springframework.test.context.TestContext;

/**
 * {@link TestContextEvent} published by the {@link EventPublishingTestExecutionListener} when
 * {@link org.springframework.test.context.TestExecutionListener#beforeTestClass(TestContext)}
 * is invoked.
 *
 * @author Frank Scheffler
 * @see org.springframework.test.context.event.annotation.BeforeTestClass @BeforeTestClass
 * @since 5.2
 */
@SuppressWarnings("serial")
public class BeforeTestClassEvent extends TestContextEvent {

	public BeforeTestClassEvent(TestContext source) {
		super(source);
	}

}
