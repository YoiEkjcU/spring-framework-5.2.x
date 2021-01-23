package org.springframework.jdbc.support.incrementer;

import javax.sql.DataSource;

import org.springframework.util.Assert;

/**
 * Abstract base class for {@link DataFieldMaxValueIncrementer} implementations that use
 * a column in a custom sequence table. Subclasses need to provide the specific handling
 * of that table in their {@link #getNextKey()} implementation.
 *
 * @author Juergen Hoeller
 * @since 2.5.3
 */
public abstract class AbstractColumnMaxValueIncrementer extends AbstractDataFieldMaxValueIncrementer {

	/**
	 * The name of the column for this sequence.
	 */
	private String columnName;

	/**
	 * The number of keys buffered in a cache.
	 */
	private int cacheSize = 1;


	/**
	 * Default constructor for bean property style usage.
	 *
	 * @see #setDataSource
	 * @see #setIncrementerName
	 * @see #setColumnName
	 */
	public AbstractColumnMaxValueIncrementer() {
	}

	/**
	 * Convenience constructor.
	 *
	 * @param dataSource      the DataSource to use
	 * @param incrementerName the name of the sequence/table to use
	 * @param columnName      the name of the column in the sequence table to use
	 */
	public AbstractColumnMaxValueIncrementer(DataSource dataSource, String incrementerName, String columnName) {
		super(dataSource, incrementerName);
		Assert.notNull(columnName, "Column name must not be null");
		this.columnName = columnName;
	}


	/**
	 * Set the name of the column in the sequence table.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Return the name of the column in the sequence table.
	 */
	public String getColumnName() {
		return this.columnName;
	}

	/**
	 * Set the number of buffered keys.
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	/**
	 * Return the number of buffered keys.
	 */
	public int getCacheSize() {
		return this.cacheSize;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if (this.columnName == null) {
			throw new IllegalArgumentException("Property 'columnName' is required");
		}
	}

}
