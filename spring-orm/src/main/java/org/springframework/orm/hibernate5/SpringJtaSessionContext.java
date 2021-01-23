package org.springframework.orm.hibernate5;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.context.internal.JTASessionContext;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Spring-specific subclass of Hibernate's JTASessionContext,
 * setting {@code FlushMode.MANUAL} for read-only transactions.
 *
 * @author Juergen Hoeller
 * @since 4.2
 */
@SuppressWarnings("serial")
public class SpringJtaSessionContext extends JTASessionContext {

	public SpringJtaSessionContext(SessionFactoryImplementor factory) {
		super(factory);
	}

	@Override
	protected Session buildOrObtainSession() {
		Session session = super.buildOrObtainSession();
		if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
			session.setHibernateFlushMode(FlushMode.MANUAL);
		}
		return session;
	}

}
