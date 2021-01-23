package org.springframework.orm.hibernate5;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.EntityManagerHolder;

/**
 * Resource holder wrapping a Hibernate {@link Session} (plus an optional {@link Transaction}).
 * {@link HibernateTransactionManager} binds instances of this class to the thread,
 * for a given {@link org.hibernate.SessionFactory}. Extends {@link EntityManagerHolder}
 * as of 5.1, automatically exposing an {@code EntityManager} handle on Hibernate 5.2+.
 *
 * <p>Note: This is an SPI class, not intended to be used by applications.
 *
 * @author Juergen Hoeller
 * @see HibernateTransactionManager
 * @see SessionFactoryUtils
 * @since 4.2
 */
public class SessionHolder extends EntityManagerHolder {

	@Nullable
	private Transaction transaction;

	@Nullable
	private FlushMode previousFlushMode;


	public SessionHolder(Session session) {
		super(session);
	}


	public Session getSession() {
		return (Session) getEntityManager();
	}

	public void setTransaction(@Nullable Transaction transaction) {
		this.transaction = transaction;
		setTransactionActive(transaction != null);
	}

	@Nullable
	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setPreviousFlushMode(@Nullable FlushMode previousFlushMode) {
		this.previousFlushMode = previousFlushMode;
	}

	@Nullable
	public FlushMode getPreviousFlushMode() {
		return this.previousFlushMode;
	}


	@Override
	public void clear() {
		super.clear();
		this.transaction = null;
		this.previousFlushMode = null;
	}

}
