package org.springframework.transaction.interceptor;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import org.springframework.core.testfixture.io.SerializationTestUtils;

/**
 * @author Rod Johnson
 */
public class TransactionAttributeSourceAdvisorTests {

	@Test
	public void serializability() throws Exception {
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionAttributes(new Properties());
		TransactionAttributeSourceAdvisor tas = new TransactionAttributeSourceAdvisor(ti);
		SerializationTestUtils.serializeAndDeserialize(tas);
	}

}
