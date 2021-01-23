package org.springframework.scripting;

/**
 * @author Juergen Hoeller
 */
public interface ConfigurableMessenger extends Messenger {

	void setMessage(String message);

}
