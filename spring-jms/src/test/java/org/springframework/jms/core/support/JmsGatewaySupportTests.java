package org.springframework.jms.core.support;

import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.junit.jupiter.api.Test;

import org.springframework.jms.core.JmsTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author Mark Pollack
 * @since 24.9.2004
 */
public class JmsGatewaySupportTests {

	@Test
	public void testJmsGatewaySupportWithConnectionFactory() throws Exception {
		ConnectionFactory mockConnectionFactory = mock(ConnectionFactory.class);
		final List<String> test = new ArrayList<>(1);
		JmsGatewaySupport gateway = new JmsGatewaySupport() {
			@Override
			protected void initGateway() {
				test.add("test");
			}
		};
		gateway.setConnectionFactory(mockConnectionFactory);
		gateway.afterPropertiesSet();
		assertThat(gateway.getConnectionFactory()).as("Correct ConnectionFactory").isEqualTo(mockConnectionFactory);
		assertThat(gateway.getJmsTemplate().getConnectionFactory()).as("Correct JmsTemplate").isEqualTo(mockConnectionFactory);
		assertThat(test.size()).as("initGateway called").isEqualTo(1);
	}

	@Test
	public void testJmsGatewaySupportWithJmsTemplate() throws Exception {
		JmsTemplate template = new JmsTemplate();
		final List<String> test = new ArrayList<>(1);
		JmsGatewaySupport gateway = new JmsGatewaySupport() {
			@Override
			protected void initGateway() {
				test.add("test");
			}
		};
		gateway.setJmsTemplate(template);
		gateway.afterPropertiesSet();
		assertThat(gateway.getJmsTemplate()).as("Correct JmsTemplate").isEqualTo(template);
		assertThat(test.size()).as("initGateway called").isEqualTo(1);
	}

}
