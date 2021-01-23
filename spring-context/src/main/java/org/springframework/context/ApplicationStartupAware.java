package org.springframework.context;

import org.springframework.beans.factory.Aware;
import org.springframework.core.metrics.ApplicationStartup;

/**
 * Interface to be implemented by any object that wishes to be notified
 * of the {@link ApplicationStartup} that it runs with.
 *
 * @author Brian Clozel
 * @see ApplicationContextAware
 * @since 5.3
 */
public interface ApplicationStartupAware extends Aware {

	/**
	 * Set the ApplicationStartup that this object runs with.
	 * <p>Invoked after population of normal bean properties but before an init
	 * callback like InitializingBean's afterPropertiesSet or a custom init-method.
	 * Invoked before ApplicationContextAware's setApplicationContext.
	 *
	 * @param applicationStartup application startup to be used by this object
	 */
	void setApplicationStartup(ApplicationStartup applicationStartup);

}
