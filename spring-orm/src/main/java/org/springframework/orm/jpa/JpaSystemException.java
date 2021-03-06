package org.springframework.orm.jpa;

import org.springframework.dao.UncategorizedDataAccessException;

/**
 * JPA-specific subclass of UncategorizedDataAccessException,
 * for JPA system errors that do not match any concrete
 * {@code org.springframework.dao} exceptions.
 *
 * @author Juergen Hoeller
 * @see EntityManagerFactoryUtils#convertJpaAccessExceptionIfPossible
 * @since 2.0
 */
@SuppressWarnings("serial")
public class JpaSystemException extends UncategorizedDataAccessException {

	public JpaSystemException(RuntimeException ex) {
		super(ex.getMessage(), ex);
	}

}
