package org.springframework.scheduling.config;

import java.util.Set;

/**
 * Common interface for exposing locally scheduled tasks.
 *
 * @author Juergen Hoeller
 * @see ScheduledTaskRegistrar
 * @see org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
 * @since 5.0.2
 */
public interface ScheduledTaskHolder {

	/**
	 * Return an overview of the tasks that have been scheduled by this instance.
	 */
	Set<ScheduledTask> getScheduledTasks();

}
