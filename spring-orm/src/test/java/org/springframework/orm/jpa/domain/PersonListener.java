package org.springframework.orm.jpa.domain;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author Juergen Hoeller
 */
public class PersonListener {

	@Autowired
	ApplicationContext context;

	@PostLoad
	public void postLoad(Person person) {
		person.postLoaded = this.context;
	}

}
