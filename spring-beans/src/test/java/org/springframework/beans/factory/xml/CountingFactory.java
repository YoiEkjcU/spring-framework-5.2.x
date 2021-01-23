package org.springframework.beans.factory.xml;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.testfixture.beans.TestBean;

/**
 * @author Juergen Hoeller
 */
public class CountingFactory implements FactoryBean<String> {

	private static int factoryBeanInstanceCount = 0;


	/**
	 * Clear static state.
	 */
	public static void reset() {
		factoryBeanInstanceCount = 0;
	}

	public static int getFactoryBeanInstanceCount() {
		return factoryBeanInstanceCount;
	}


	public CountingFactory() {
		factoryBeanInstanceCount++;
	}

	public void setTestBean(TestBean tb) {
		if (tb.getSpouse() == null) {
			throw new IllegalStateException("TestBean needs to have spouse");
		}
	}


	@Override
	public String getObject() {
		return "myString";
	}

	@Override
	public Class<String> getObjectType() {
		return String.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
