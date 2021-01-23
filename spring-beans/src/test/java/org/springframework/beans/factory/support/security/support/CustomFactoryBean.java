package org.springframework.beans.factory.support.security.support;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author Costin Leau
 */
public class CustomFactoryBean implements FactoryBean<Properties> {

	@Override
	public Properties getObject() throws Exception {
		return System.getProperties();
	}

	@Override
	public Class<Properties> getObjectType() {
		System.setProperty("factory.object.type", "true");
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
