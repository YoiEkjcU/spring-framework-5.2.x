package org.springframework.scheduling.annotation;

import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Optional interface to be implemented by @{@link
 * org.springframework.context.annotation.Configuration Configuration} classes annotated
 * with @{@link EnableScheduling}. Typically used for setting a specific
 * {@link org.springframework.scheduling.TaskScheduler TaskScheduler} bean to be used when
 * executing scheduled tasks or for registering scheduled tasks in a <em>programmatic</em>
 * fashion as opposed to the <em>declarative</em> approach of using the @{@link Scheduled}
 * annotation. For example, this may be necessary when implementing {@link
 * org.springframework.scheduling.Trigger Trigger}-based tasks, which are not supported by
 * the {@code @Scheduled} annotation.
 *
 * <p>See @{@link EnableScheduling} for detailed usage examples.
 *
 * @author Chris Beams
 * @see EnableScheduling
 * @see ScheduledTaskRegistrar
 * @since 3.1
 */
@FunctionalInterface
public interface SchedulingConfigurer {

	/**
	 * Callback allowing a {@link org.springframework.scheduling.TaskScheduler
	 * TaskScheduler} and specific {@link org.springframework.scheduling.config.Task Task}
	 * instances to be registered against the given the {@link ScheduledTaskRegistrar}.
	 *
	 * @param taskRegistrar the registrar to be configured.
	 */
	void configureTasks(ScheduledTaskRegistrar taskRegistrar);

}
