package org.springframework.util.concurrent;

import org.springframework.lang.Nullable;

/**
 * Success callback for a {@link ListenableFuture}.
 *
 * @param <T> the result type
 * @author Sebastien Deleuze
 * @since 4.1
 */
@FunctionalInterface
public interface SuccessCallback<T> {

	/**
	 * Called when the {@link ListenableFuture} completes with success.
	 * <p>Note that Exceptions raised by this method are ignored.
	 *
	 * @param result the result
	 */
	void onSuccess(@Nullable T result);

}
