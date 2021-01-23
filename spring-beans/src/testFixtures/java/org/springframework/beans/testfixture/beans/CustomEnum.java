package org.springframework.beans.testfixture.beans;

/**
 * @author Juergen Hoeller
 */
public enum CustomEnum {

	VALUE_1, VALUE_2;

	@Override
	public String toString() {
		return "CustomEnum: " + name();
	}

}
