package org.springframework.http.codec.support;

import java.util.List;

import org.springframework.core.codec.Encoder;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.ServerSentEventHttpMessageWriter;
import org.springframework.http.codec.multipart.DefaultPartHttpMessageReader;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;
import org.springframework.http.codec.multipart.PartHttpMessageWriter;
import org.springframework.lang.Nullable;

/**
 * Default implementation of {@link ServerCodecConfigurer.ServerDefaultCodecs}.
 *
 * @author Rossen Stoyanchev
 */
class ServerDefaultCodecsImpl extends BaseDefaultCodecs implements ServerCodecConfigurer.ServerDefaultCodecs {

	@Nullable
	private HttpMessageReader<?> multipartReader;

	@Nullable
	private Encoder<?> sseEncoder;


	ServerDefaultCodecsImpl() {
	}

	ServerDefaultCodecsImpl(ServerDefaultCodecsImpl other) {
		super(other);
		this.multipartReader = other.multipartReader;
		this.sseEncoder = other.sseEncoder;
	}


	@Override
	public void multipartReader(HttpMessageReader<?> reader) {
		this.multipartReader = reader;
	}

	@Override
	public void serverSentEventEncoder(Encoder<?> encoder) {
		this.sseEncoder = encoder;
	}


	@Override
	protected void extendTypedReaders(List<HttpMessageReader<?>> typedReaders) {
		if (this.multipartReader != null) {
			addCodec(typedReaders, this.multipartReader);
			return;
		}
		DefaultPartHttpMessageReader partReader = new DefaultPartHttpMessageReader();
		addCodec(typedReaders, partReader);
		addCodec(typedReaders, new MultipartHttpMessageReader(partReader));
	}

	@Override
	protected void extendTypedWriters(List<HttpMessageWriter<?>> typedWriters) {
		addCodec(typedWriters, new PartHttpMessageWriter());
	}

	@Override
	protected void extendObjectWriters(List<HttpMessageWriter<?>> objectWriters) {
		objectWriters.add(new ServerSentEventHttpMessageWriter(getSseEncoder()));
	}

	@Nullable
	private Encoder<?> getSseEncoder() {
		return this.sseEncoder != null ? this.sseEncoder : jackson2Present ? getJackson2JsonEncoder() : null;
	}

}
