package org.springframework.beans.testfixture.beans;


/**
 * @author Juergen Hoeller
 * @since 15.03.2005
 */
public class CountingTestBean extends TestBean {

	public static int count = 0;

	public CountingTestBean() {
		count++;
	}

}
