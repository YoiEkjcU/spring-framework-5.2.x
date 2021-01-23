package org.springframework.messaging.tcp.reactor;

import io.netty.buffer.ByteBuf;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

import org.springframework.messaging.Message;
import org.springframework.messaging.tcp.TcpConnection;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.MonoToListenableFutureAdapter;

/**
 * Reactor Netty based implementation of {@link TcpConnection}.
 *
 * @param <P> the type of payload for outbound messages
 * @author Rossen Stoyanchev
 * @since 5.0
 */
public class ReactorNettyTcpConnection<P> implements TcpConnection<P> {

	private final NettyInbound inbound;

	private final NettyOutbound outbound;

	private final ReactorNettyCodec<P> codec;

	private final MonoProcessor<Void> closeProcessor;


	public ReactorNettyTcpConnection(NettyInbound inbound, NettyOutbound outbound,
									 ReactorNettyCodec<P> codec, MonoProcessor<Void> closeProcessor) {

		this.inbound = inbound;
		this.outbound = outbound;
		this.codec = codec;
		this.closeProcessor = closeProcessor;
	}


	@Override
	public ListenableFuture<Void> send(Message<P> message) {
		ByteBuf byteBuf = this.outbound.alloc().buffer();
		this.codec.encode(message, byteBuf);
		Mono<Void> sendCompletion = this.outbound.send(Mono.just(byteBuf)).then();
		return new MonoToListenableFutureAdapter<>(sendCompletion);
	}

	@Override
	public void onReadInactivity(Runnable runnable, long inactivityDuration) {
		this.inbound.withConnection(conn -> conn.onReadIdle(inactivityDuration, runnable));
	}

	@Override
	public void onWriteInactivity(Runnable runnable, long inactivityDuration) {
		this.inbound.withConnection(conn -> conn.onWriteIdle(inactivityDuration, runnable));
	}

	@Override
	public void close() {
		this.closeProcessor.onComplete();
	}

}
