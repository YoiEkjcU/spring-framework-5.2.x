package org.springframework.cache.aspectj;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.cache.jcache.config.AbstractJCacheConfiguration;
import org.springframework.cache.jcache.interceptor.JCacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * {@code @Configuration} class that registers the Spring infrastructure beans necessary
 * to enable AspectJ-based annotation-driven cache management for standard JSR-107
 * annotations.
 *
 * @author Stephane Nicoll
 * @see org.springframework.cache.annotation.EnableCaching
 * @see org.springframework.cache.annotation.CachingConfigurationSelector
 * @since 4.1
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AspectJJCacheConfiguration extends AbstractJCacheConfiguration {

	@Bean(name = CacheManagementConfigUtils.JCACHE_ASPECT_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public JCacheCacheAspect cacheAspect(JCacheOperationSource jCacheOperationSource) {
		JCacheCacheAspect cacheAspect = JCacheCacheAspect.aspectOf();
		cacheAspect.setCacheOperationSource(jCacheOperationSource);
		return cacheAspect;
	}
}
