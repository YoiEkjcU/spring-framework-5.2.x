package org.springframework.scheduling.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.NoOpRunnable;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * @author Rick Evans
 * @author Juergen Hoeller
 */
class ConcurrentTaskExecutorTests extends AbstractSchedulingTaskExecutorTests {

	private final ThreadPoolExecutor concurrentExecutor =
			new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


	@Override
	protected AsyncListenableTaskExecutor buildExecutor() {
		concurrentExecutor.setThreadFactory(new CustomizableThreadFactory(this.threadNamePrefix));
		return new ConcurrentTaskExecutor(concurrentExecutor);
	}

	@Override
	@AfterEach
	void shutdownExecutor() {
		for (Runnable task : concurrentExecutor.shutdownNow()) {
			if (task instanceof RunnableFuture) {
				((RunnableFuture<?>) task).cancel(true);
			}
		}
	}


	@Test
	void zeroArgCtorResultsInDefaultTaskExecutorBeingUsed() {
		ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor();
		assertThatCode(() -> executor.execute(new NoOpRunnable())).doesNotThrowAnyException();
	}

	@Test
	void passingNullExecutorToCtorResultsInDefaultTaskExecutorBeingUsed() {
		ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor(null);
		assertThatCode(() -> executor.execute(new NoOpRunnable())).doesNotThrowAnyException();
	}

	@Test
	void passingNullExecutorToSetterResultsInDefaultTaskExecutorBeingUsed() {
		ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor();
		executor.setConcurrentExecutor(null);
		assertThatCode(() -> executor.execute(new NoOpRunnable())).doesNotThrowAnyException();
	}

}
