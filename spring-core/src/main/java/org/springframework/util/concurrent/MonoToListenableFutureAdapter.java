package org.springframework.util.concurrent;

import reactor.core.publisher.Mono;

/**
 * Adapts a {@link Mono} into a {@link ListenableFuture} by obtaining a
 * {@code CompletableFuture} from the {@code Mono} via {@link Mono#toFuture()}
 * and then adapting it with {@link CompletableToListenableFutureAdapter}.
 *
 * @param <T> the object type
 * @author Rossen Stoyanchev
 * @author Stephane Maldini
 * @since 5.1
 */
public class MonoToListenableFutureAdapter<T> extends CompletableToListenableFutureAdapter<T> {

	public MonoToListenableFutureAdapter(Mono<T> mono) {
		super(mono.toFuture());
	}

}
