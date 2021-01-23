package org.springframework.core.codec;

import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

/**
 * Decoder for {@link ByteBuf ByteBufs}.
 *
 * @author Vladislav Kisel
 * @since 5.3
 */
public class NettyByteBufDecoder extends AbstractDataBufferDecoder<ByteBuf> {

	public NettyByteBufDecoder() {
		super(MimeTypeUtils.ALL);
	}


	@Override
	public boolean canDecode(ResolvableType elementType, @Nullable MimeType mimeType) {
		return (ByteBuf.class.isAssignableFrom(elementType.toClass()) &&
				super.canDecode(elementType, mimeType));
	}

	@Override
	public ByteBuf decode(DataBuffer dataBuffer, ResolvableType elementType,
						  @Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		if (logger.isDebugEnabled()) {
			logger.debug(Hints.getLogPrefix(hints) + "Read " + dataBuffer.readableByteCount() + " bytes");
		}
		if (dataBuffer instanceof NettyDataBuffer) {
			return ((NettyDataBuffer) dataBuffer).getNativeBuffer();
		}
		ByteBuf byteBuf;
		byte[] bytes = new byte[dataBuffer.readableByteCount()];
		dataBuffer.read(bytes);
		byteBuf = Unpooled.wrappedBuffer(bytes);
		DataBufferUtils.release(dataBuffer);
		return byteBuf;
	}

}
