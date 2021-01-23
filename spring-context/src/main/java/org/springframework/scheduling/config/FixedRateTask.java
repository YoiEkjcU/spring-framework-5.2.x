package org.springframework.scheduling.config;

/**
 * Specialization of {@link IntervalTask} for fixed-rate semantics.
 *
 * @author Juergen Hoeller
 * @see org.springframework.scheduling.annotation.Scheduled#fixedRate()
 * @see ScheduledTaskRegistrar#addFixedRateTask(IntervalTask)
 * @since 5.0.2
 */
public class FixedRateTask extends IntervalTask {

	/**
	 * Create a new {@code FixedRateTask}.
	 *
	 * @param runnable     the underlying task to execute
	 * @param interval     how often in milliseconds the task should be executed
	 * @param initialDelay the initial delay before first execution of the task
	 */
	public FixedRateTask(Runnable runnable, long interval, long initialDelay) {
		super(runnable, interval, initialDelay);
	}

}
