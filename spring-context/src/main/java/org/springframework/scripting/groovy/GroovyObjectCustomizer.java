package org.springframework.scripting.groovy;

import groovy.lang.GroovyObject;

/**
 * Strategy used by {@link GroovyScriptFactory} to allow the customization of
 * a created {@link GroovyObject}.
 *
 * <p>This is useful to allow the authoring of DSLs, the replacement of missing
 * methods, and so forth. For example, a custom {@link groovy.lang.MetaClass}
 * could be specified.
 *
 * @author Rod Johnson
 * @see GroovyScriptFactory
 * @since 2.0.2
 */
@FunctionalInterface
public interface GroovyObjectCustomizer {

	/**
	 * Customize the supplied {@link GroovyObject}.
	 * <p>For example, this can be used to set a custom metaclass to
	 * handle missing methods.
	 *
	 * @param goo the {@code GroovyObject} to customize
	 */
	void customize(GroovyObject goo);

}
