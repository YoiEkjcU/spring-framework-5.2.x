package org.springframework.transaction.jta;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.util.Assert;

/**
 * Adapter for a JTA UserTransaction handle, taking a JTA
 * {@link javax.transaction.TransactionManager} reference and creating
 * a JTA {@link javax.transaction.UserTransaction} handle for it.
 *
 * <p>The JTA UserTransaction interface is an exact subset of the JTA
 * TransactionManager interface. Unfortunately, it does not serve as
 * super-interface of TransactionManager, though, which requires an
 * adapter such as this class to be used when intending to talk to
 * a TransactionManager handle through the UserTransaction interface.
 *
 * <p>Used internally by Spring's {@link JtaTransactionManager} for certain
 * scenarios. Not intended for direct use in application code.
 *
 * @author Juergen Hoeller
 * @since 1.1.5
 */
public class UserTransactionAdapter implements UserTransaction {

	private final TransactionManager transactionManager;


	/**
	 * Create a new UserTransactionAdapter for the given TransactionManager.
	 *
	 * @param transactionManager the JTA TransactionManager to wrap
	 */
	public UserTransactionAdapter(TransactionManager transactionManager) {
		Assert.notNull(transactionManager, "TransactionManager must not be null");
		this.transactionManager = transactionManager;
	}

	/**
	 * Return the JTA TransactionManager that this adapter delegates to.
	 */
	public final TransactionManager getTransactionManager() {
		return this.transactionManager;
	}


	@Override
	public void setTransactionTimeout(int timeout) throws SystemException {
		this.transactionManager.setTransactionTimeout(timeout);
	}

	@Override
	public void begin() throws NotSupportedException, SystemException {
		this.transactionManager.begin();
	}

	@Override
	public void commit()
			throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
			SecurityException, SystemException {
		this.transactionManager.commit();
	}

	@Override
	public void rollback() throws SecurityException, SystemException {
		this.transactionManager.rollback();
	}

	@Override
	public void setRollbackOnly() throws SystemException {
		this.transactionManager.setRollbackOnly();
	}

	@Override
	public int getStatus() throws SystemException {
		return this.transactionManager.getStatus();
	}

}
