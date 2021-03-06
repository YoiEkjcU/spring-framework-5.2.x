package org.springframework.jmx.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.management.MBeanServer;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jmx.MBeanServerNotFoundException;
import org.springframework.lang.Nullable;

/**
 * {@link FactoryBean} that obtains a WebSphere {@link javax.management.MBeanServer}
 * reference through WebSphere's proprietary {@code AdminServiceFactory} API,
 * available on WebSphere 5.1 and higher.
 *
 * <p>Exposes the {@code MBeanServer} for bean references.
 *
 * <p>This {@code FactoryBean} is a direct alternative to {@link MBeanServerFactoryBean},
 * which uses standard JMX 1.2 API to access the platform's {@link MBeanServer}.
 *
 * <p>See the javadocs for WebSphere's
 * <a href="https://www.ibm.com/support/knowledgecenter/SSEQTJ_9.0.0/com.ibm.websphere.javadoc.doc/web/apidocs/com/ibm/websphere/management/AdminServiceFactory.html">{@code AdminServiceFactory}</a>
 * and
 * <a href="https://www.ibm.com/support/knowledgecenter/SSEQTJ_9.0.0/com.ibm.websphere.javadoc.doc/web/apidocs/com/ibm/websphere/management/MBeanFactory.html">{@code MBeanFactory}</a>.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @see javax.management.MBeanServer
 * @see MBeanServerFactoryBean
 * @since 2.0.3
 */
public class WebSphereMBeanServerFactoryBean implements FactoryBean<MBeanServer>, InitializingBean {

	private static final String ADMIN_SERVICE_FACTORY_CLASS = "com.ibm.websphere.management.AdminServiceFactory";

	private static final String GET_MBEAN_FACTORY_METHOD = "getMBeanFactory";

	private static final String GET_MBEAN_SERVER_METHOD = "getMBeanServer";


	@Nullable
	private MBeanServer mbeanServer;


	@Override
	public void afterPropertiesSet() throws MBeanServerNotFoundException {
		try {
			/*
			 * this.mbeanServer = AdminServiceFactory.getMBeanFactory().getMBeanServer();
			 */
			Class<?> adminServiceClass = getClass().getClassLoader().loadClass(ADMIN_SERVICE_FACTORY_CLASS);
			Method getMBeanFactoryMethod = adminServiceClass.getMethod(GET_MBEAN_FACTORY_METHOD);
			Object mbeanFactory = getMBeanFactoryMethod.invoke(null);
			Method getMBeanServerMethod = mbeanFactory.getClass().getMethod(GET_MBEAN_SERVER_METHOD);
			this.mbeanServer = (MBeanServer) getMBeanServerMethod.invoke(mbeanFactory);
		} catch (ClassNotFoundException ex) {
			throw new MBeanServerNotFoundException("Could not find WebSphere's AdminServiceFactory class", ex);
		} catch (InvocationTargetException ex) {
			throw new MBeanServerNotFoundException(
					"WebSphere's AdminServiceFactory.getMBeanFactory/getMBeanServer method failed", ex.getTargetException());
		} catch (Exception ex) {
			throw new MBeanServerNotFoundException(
					"Could not access WebSphere's AdminServiceFactory.getMBeanFactory/getMBeanServer method", ex);
		}
	}


	@Override
	@Nullable
	public MBeanServer getObject() {
		return this.mbeanServer;
	}

	@Override
	public Class<? extends MBeanServer> getObjectType() {
		return (this.mbeanServer != null ? this.mbeanServer.getClass() : MBeanServer.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
