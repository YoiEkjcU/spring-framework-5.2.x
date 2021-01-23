package org.springframework.test.context.junit4.profile.importresource;

import org.springframework.beans.testfixture.beans.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Juergen Hoeller
 * @since 3.1
 */
@Configuration
@ImportResource("org/springframework/test/context/junit4/profile/importresource/import.xml")
public class DefaultProfileConfig {

	@Bean
	public Pet pet() {
		return new Pet("Fido");
	}

}
