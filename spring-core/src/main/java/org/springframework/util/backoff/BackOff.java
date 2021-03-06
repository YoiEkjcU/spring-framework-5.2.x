package org.springframework.util.backoff;

/**
 * Provide a {@link BackOffExecution} that indicates the rate at which
 * an operation should be retried.
 *
 * <p>Users of this interface are expected to use it like this:
 *
 * <pre class="code">
 * BackOffExecution exec = backOff.start();
 *
 * // In the operation recovery/retry loop:
 * long waitInterval = exec.nextBackOff();
 * if (waitInterval == BackOffExecution.STOP) {
 *     // do not retry operation
 * }
 * else {
 *     // sleep, e.g. Thread.sleep(waitInterval)
 *     // retry operation
 * }
 * }</pre>
 * <p>
 * Once the underlying operation has completed successfully,
 * the execution instance can be simply discarded.
 *
 * @author Stephane Nicoll
 * @see BackOffExecution
 * @since 4.1
 */
@FunctionalInterface
public interface BackOff {

	/**
	 * Start a new back off execution.
	 *
	 * @return a fresh {@link BackOffExecution} ready to be used
	 */
	BackOffExecution start();

}
