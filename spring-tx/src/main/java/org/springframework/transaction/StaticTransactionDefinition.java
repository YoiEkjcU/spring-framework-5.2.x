package org.springframework.transaction;

/**
 * A static unmodifiable transaction definition.
 *
 * @author Juergen Hoeller
 * @see TransactionDefinition#withDefaults()
 * @since 5.2
 */
final class StaticTransactionDefinition implements TransactionDefinition {

	static final StaticTransactionDefinition INSTANCE = new StaticTransactionDefinition();

	private StaticTransactionDefinition() {
	}

}
