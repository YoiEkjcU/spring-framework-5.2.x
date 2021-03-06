package org.springframework.scheduling;

import java.util.Date;

import org.springframework.lang.Nullable;

/**
 * Common interface for trigger objects that determine the next execution time
 * of a task that they get associated with.
 *
 * @author Juergen Hoeller
 * @see TaskScheduler#schedule(Runnable, Trigger)
 * @see org.springframework.scheduling.support.CronTrigger
 * @since 3.0
 */
public interface Trigger {

	/**
	 * Determine the next execution time according to the given trigger context.
	 *
	 * @param triggerContext context object encapsulating last execution times
	 *                       and last completion time
	 * @return the next execution time as defined by the trigger,
	 * or {@code null} if the trigger won't fire anymore
	 */
	@Nullable
	Date nextExecutionTime(TriggerContext triggerContext);

}
