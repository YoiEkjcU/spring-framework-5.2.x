package org.springframework.jdbc.datasource.lookup;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link DataSourceLookup} implementation based on a Spring {@link BeanFactory}.
 *
 * <p>Will lookup Spring managed beans identified by bean name,
 * expecting them to be of type {@code javax.sql.DataSource}.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @see org.springframework.beans.factory.BeanFactory
 * @since 2.0
 */
public class BeanFactoryDataSourceLookup implements DataSourceLookup, BeanFactoryAware {

	@Nullable
	private BeanFactory beanFactory;


	/**
	 * Create a new instance of the {@link BeanFactoryDataSourceLookup} class.
	 * <p>The BeanFactory to access must be set via {@code setBeanFactory}.
	 *
	 * @see #setBeanFactory
	 */
	public BeanFactoryDataSourceLookup() {
	}

	/**
	 * Create a new instance of the {@link BeanFactoryDataSourceLookup} class.
	 * <p>Use of this constructor is redundant if this object is being created
	 * by a Spring IoC container, as the supplied {@link BeanFactory} will be
	 * replaced by the {@link BeanFactory} that creates it (c.f. the
	 * {@link BeanFactoryAware} contract). So only use this constructor if you
	 * are using this class outside the context of a Spring IoC container.
	 *
	 * @param beanFactory the bean factory to be used to lookup {@link DataSource DataSources}
	 */
	public BeanFactoryDataSourceLookup(BeanFactory beanFactory) {
		Assert.notNull(beanFactory, "BeanFactory is required");
		this.beanFactory = beanFactory;
	}


	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}


	@Override
	public DataSource getDataSource(String dataSourceName) throws DataSourceLookupFailureException {
		Assert.state(this.beanFactory != null, "BeanFactory is required");
		try {
			return this.beanFactory.getBean(dataSourceName, DataSource.class);
		} catch (BeansException ex) {
			throw new DataSourceLookupFailureException(
					"Failed to look up DataSource bean with name '" + dataSourceName + "'", ex);
		}
	}

}
