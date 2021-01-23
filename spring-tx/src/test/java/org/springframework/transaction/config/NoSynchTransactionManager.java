package org.springframework.transaction.config;

import org.springframework.transaction.testfixture.CallCountingTransactionManager;

/**
 * @author Juergen Hoeller
 */
@NoSynch
@SuppressWarnings("serial")
public class NoSynchTransactionManager extends CallCountingTransactionManager {

	public NoSynchTransactionManager() {
		setTransactionSynchronization(CallCountingTransactionManager.SYNCHRONIZATION_NEVER);
	}

}
