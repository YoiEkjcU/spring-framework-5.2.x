package org.springframework.core.task;

/**
 * A no-op {@link Runnable} implementation.
 *
 * @author Rick Evans
 */
public class NoOpRunnable implements Runnable {

	@Override
	public void run() {
		// explicit no-op
	}

}
