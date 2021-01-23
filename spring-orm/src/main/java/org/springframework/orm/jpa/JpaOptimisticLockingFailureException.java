package org.springframework.orm.jpa;

import javax.persistence.OptimisticLockException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

/**
 * JPA-specific subclass of ObjectOptimisticLockingFailureException.
 * Converts JPA's OptimisticLockException.
 *
 * @author Juergen Hoeller
 * @see EntityManagerFactoryUtils#convertJpaAccessExceptionIfPossible
 * @since 2.0
 */
@SuppressWarnings("serial")
public class JpaOptimisticLockingFailureException extends ObjectOptimisticLockingFailureException {

	public JpaOptimisticLockingFailureException(OptimisticLockException ex) {
		super(ex.getMessage(), ex);
	}

}
