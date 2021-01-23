package org.springframework.context.annotation.componentscan.level1;

import org.springframework.beans.testfixture.beans.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.springframework.context.annotation.componentscan.level2")
public class Level1Config {
	@Bean
	public TestBean level1Bean() {
		return new TestBean("level1Bean");
	}
}
