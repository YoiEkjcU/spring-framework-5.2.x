package org.springframework.core.env;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Exception thrown when required properties are not found.
 *
 * @author Chris Beams
 * @see ConfigurablePropertyResolver#setRequiredProperties(String...)
 * @see ConfigurablePropertyResolver#validateRequiredProperties()
 * @see org.springframework.context.support.AbstractApplicationContext#prepareRefresh()
 * @since 3.1
 */
@SuppressWarnings("serial")
public class MissingRequiredPropertiesException extends IllegalStateException {

	private final Set<String> missingRequiredProperties = new LinkedHashSet<>();


	void addMissingRequiredProperty(String key) {
		this.missingRequiredProperties.add(key);
	}

	@Override
	public String getMessage() {
		return "The following properties were declared as required but could not be resolved: " +
				getMissingRequiredProperties();
	}

	/**
	 * Return the set of properties marked as required but not present
	 * upon validation.
	 *
	 * @see ConfigurablePropertyResolver#setRequiredProperties(String...)
	 * @see ConfigurablePropertyResolver#validateRequiredProperties()
	 */
	public Set<String> getMissingRequiredProperties() {
		return this.missingRequiredProperties;
	}

}
