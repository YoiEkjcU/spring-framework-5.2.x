package org.springframework.scheduling.concurrent;

import org.springframework.core.task.AsyncListenableTaskExecutor;

/**
 * @author Juergen Hoeller
 * @since 5.0.5
 */
class ThreadPoolTaskExecutorTests extends AbstractSchedulingTaskExecutorTests {

	@Override
	protected AsyncListenableTaskExecutor buildExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix(this.threadNamePrefix);
		executor.setMaxPoolSize(1);
		executor.afterPropertiesSet();
		return executor;
	}

}
