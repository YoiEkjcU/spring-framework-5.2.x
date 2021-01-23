package org.springframework.context.annotation4;

import org.springframework.beans.testfixture.beans.TestBean;
import org.springframework.context.annotation.Bean;

/**
 * Class to test that @FactoryMethods are detected only when inside a class with an @Component
 * class annotation.
 *
 * @author Mark Pollack
 */
public class SimpleBean {

	// This should *not* recognized as a bean since it does not reside inside an @Component
	@Bean
	public TestBean getPublicInstance() {
		return new TestBean("publicInstance");
	}

}
