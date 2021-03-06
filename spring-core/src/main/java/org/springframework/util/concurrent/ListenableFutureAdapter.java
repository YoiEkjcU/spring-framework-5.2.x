package org.springframework.util.concurrent;

import java.util.concurrent.ExecutionException;

import org.springframework.lang.Nullable;

/**
 * Abstract class that adapts a {@link ListenableFuture} parameterized over S into a
 * {@code ListenableFuture} parameterized over T. All methods are delegated to the
 * adaptee, where {@link #get()}, {@link #get(long, java.util.concurrent.TimeUnit)},
 * and {@link ListenableFutureCallback#onSuccess(Object)} call {@link #adapt(Object)}
 * on the adaptee's result.
 *
 * @param <T> the type of this {@code Future}
 * @param <S> the type of the adaptee's {@code Future}
 * @author Arjen Poutsma
 * @since 4.0
 */
public abstract class ListenableFutureAdapter<T, S> extends FutureAdapter<T, S> implements ListenableFuture<T> {

	/**
	 * Construct a new {@code ListenableFutureAdapter} with the given adaptee.
	 *
	 * @param adaptee the future to adapt to
	 */
	protected ListenableFutureAdapter(ListenableFuture<S> adaptee) {
		super(adaptee);
	}


	@Override
	public void addCallback(final ListenableFutureCallback<? super T> callback) {
		addCallback(callback, callback);
	}

	@Override
	public void addCallback(final SuccessCallback<? super T> successCallback, final FailureCallback failureCallback) {
		ListenableFuture<S> listenableAdaptee = (ListenableFuture<S>) getAdaptee();
		listenableAdaptee.addCallback(new ListenableFutureCallback<S>() {
			@Override
			public void onSuccess(@Nullable S result) {
				T adapted = null;
				if (result != null) {
					try {
						adapted = adaptInternal(result);
					} catch (ExecutionException ex) {
						Throwable cause = ex.getCause();
						onFailure(cause != null ? cause : ex);
						return;
					} catch (Throwable ex) {
						onFailure(ex);
						return;
					}
				}
				successCallback.onSuccess(adapted);
			}

			@Override
			public void onFailure(Throwable ex) {
				failureCallback.onFailure(ex);
			}
		});
	}

}
