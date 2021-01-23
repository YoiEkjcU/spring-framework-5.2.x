package org.springframework.messaging.core;

import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

/**
 * Operations for sending messages to and receiving the reply from a destination.
 *
 * @param <D> the type of destination
 * @author Mark Fisher
 * @author Rossen Stoyanchev
 * @see GenericMessagingTemplate
 * @since 4.0
 */
public interface MessageRequestReplyOperations<D> {

	/**
	 * Send a request message and receive the reply from a default destination.
	 *
	 * @param requestMessage the message to send
	 * @return the reply, possibly {@code null} if the message could not be received,
	 * for example due to a timeout
	 */
	@Nullable
	Message<?> sendAndReceive(Message<?> requestMessage) throws MessagingException;

	/**
	 * Send a request message and receive the reply from the given destination.
	 *
	 * @param destination    the target destination
	 * @param requestMessage the message to send
	 * @return the reply, possibly {@code null} if the message could not be received,
	 * for example due to a timeout
	 */
	@Nullable
	Message<?> sendAndReceive(D destination, Message<?> requestMessage) throws MessagingException;

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter}, send
	 * it as a {@link Message} to a default destination, receive the reply and convert
	 * its body of the specified target class.
	 *
	 * @param request     payload for the request message to send
	 * @param targetClass the target type to convert the payload of the reply to
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 */
	@Nullable
	<T> T convertSendAndReceive(Object request, Class<T> targetClass) throws MessagingException;

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter}, send
	 * it as a {@link Message} to the given destination, receive the reply and convert
	 * its body of the specified target class.
	 *
	 * @param destination the target destination
	 * @param request     payload for the request message to send
	 * @param targetClass the target type to convert the payload of the reply to
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 */
	@Nullable
	<T> T convertSendAndReceive(D destination, Object request, Class<T> targetClass) throws MessagingException;

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter}, send
	 * it as a {@link Message} with the given headers, to the specified destination,
	 * receive the reply and convert its body of the specified target class.
	 *
	 * @param destination the target destination
	 * @param request     payload for the request message to send
	 * @param headers     the headers for the request message to send
	 * @param targetClass the target type to convert the payload of the reply to
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 */
	@Nullable
	<T> T convertSendAndReceive(
			D destination, Object request, @Nullable Map<String, Object> headers, Class<T> targetClass)
			throws MessagingException;

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * apply the given post processor and send the resulting {@link Message} to a
	 * default destination, receive the reply and convert its body of the given
	 * target class.
	 *
	 * @param request              payload for the request message to send
	 * @param targetClass          the target type to convert the payload of the reply to
	 * @param requestPostProcessor post process to apply to the request message
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 */
	@Nullable
	<T> T convertSendAndReceive(
			Object request, Class<T> targetClass, @Nullable MessagePostProcessor requestPostProcessor)
			throws MessagingException;

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * apply the given post processor and send the resulting {@link Message} to the
	 * given destination, receive the reply and convert its body of the given
	 * target class.
	 *
	 * @param destination          the target destination
	 * @param request              payload for the request message to send
	 * @param targetClass          the target type to convert the payload of the reply to
	 * @param requestPostProcessor post process to apply to the request message
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 */
	@Nullable
	<T> T convertSendAndReceive(D destination, Object request, Class<T> targetClass,
								MessagePostProcessor requestPostProcessor) throws MessagingException;

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * wrap it as a message with the given headers, apply the given post processor
	 * and send the resulting {@link Message} to the specified destination, receive
	 * the reply and convert its body of the given target class.
	 *
	 * @param destination          the target destination
	 * @param request              payload for the request message to send
	 * @param targetClass          the target type to convert the payload of the reply to
	 * @param requestPostProcessor post process to apply to the request message
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 */
	@Nullable
	<T> T convertSendAndReceive(
			D destination, Object request, @Nullable Map<String, Object> headers, Class<T> targetClass,
			@Nullable MessagePostProcessor requestPostProcessor) throws MessagingException;

}
