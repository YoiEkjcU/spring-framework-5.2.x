package org.springframework.messaging.simp.user;

/**
 * A {@link java.security.Principal} can also implement this contract when
 * {@link java.security.Principal#getName() getName()} isn't globally unique
 * and therefore not suited for use with "user" destinations.
 *
 * @author Rossen Stoyanchev
 * @see org.springframework.messaging.simp.user.UserDestinationResolver
 * @since 4.0.1
 */
public interface DestinationUserNameProvider {

	/**
	 * Return a globally unique user name for use with "user" destinations.
	 */
	String getDestinationUserName();

}
