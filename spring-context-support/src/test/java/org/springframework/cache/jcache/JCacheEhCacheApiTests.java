package org.springframework.cache.jcache;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.context.testfixture.cache.AbstractValueAdaptingCacheTests;

/**
 * @author Stephane Nicoll
 */
public class JCacheEhCacheApiTests extends AbstractValueAdaptingCacheTests<JCacheCache> {

	private CacheManager cacheManager;

	private Cache<Object, Object> nativeCache;

	private JCacheCache cache;

	private JCacheCache cacheNoNull;


	@BeforeEach
	public void setup() {
		this.cacheManager = getCachingProvider().getCacheManager();
		this.cacheManager.createCache(CACHE_NAME, new MutableConfiguration<>());
		this.cacheManager.createCache(CACHE_NAME_NO_NULL, new MutableConfiguration<>());
		this.nativeCache = this.cacheManager.getCache(CACHE_NAME);
		this.cache = new JCacheCache(this.nativeCache);
		Cache<Object, Object> nativeCacheNoNull =
				this.cacheManager.getCache(CACHE_NAME_NO_NULL);
		this.cacheNoNull = new JCacheCache(nativeCacheNoNull, false);
	}

	protected CachingProvider getCachingProvider() {
		return Caching.getCachingProvider("org.ehcache.jcache.JCacheCachingProvider");
	}

	@AfterEach
	public void shutdown() {
		if (this.cacheManager != null) {
			this.cacheManager.close();
		}
	}

	@Override
	protected JCacheCache getCache() {
		return getCache(true);
	}

	@Override
	protected JCacheCache getCache(boolean allowNull) {
		return allowNull ? this.cache : this.cacheNoNull;
	}

	@Override
	protected Object getNativeCache() {
		return this.nativeCache;
	}

}
