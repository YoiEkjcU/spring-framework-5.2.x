package org.springframework.util.concurrent;

import java.time.Duration;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MonoToListenableFutureAdapter}.
 * @author Rossen Stoyanchev
 */
class MonoToListenableFutureAdapterTests {

	@Test
	void success() {
		String expected = "one";
		AtomicReference<Object> actual = new AtomicReference<>();
		ListenableFuture<String> future = new MonoToListenableFutureAdapter<>(Mono.just(expected));
		future.addCallback(actual::set, actual::set);

		assertThat(actual.get()).isEqualTo(expected);
	}

	@Test
	void failure() {
		Throwable expected = new IllegalStateException("oops");
		AtomicReference<Object> actual = new AtomicReference<>();
		ListenableFuture<String> future = new MonoToListenableFutureAdapter<>(Mono.error(expected));
		future.addCallback(actual::set, actual::set);

		assertThat(actual.get()).isEqualTo(expected);
	}

	@Test
	void cancellation() {
		Mono<Long> mono = Mono.delay(Duration.ofSeconds(60));
		Future<Long> future = new MonoToListenableFutureAdapter<>(mono);

		assertThat(future.cancel(true)).isTrue();
		assertThat(future.isCancelled()).isTrue();
	}

	@Test
	void cancellationAfterTerminated() {
		Future<Void> future = new MonoToListenableFutureAdapter<>(Mono.empty());

		assertThat(future.cancel(true)).as("Should return false if task already completed").isFalse();
		assertThat(future.isCancelled()).isFalse();
	}

}
