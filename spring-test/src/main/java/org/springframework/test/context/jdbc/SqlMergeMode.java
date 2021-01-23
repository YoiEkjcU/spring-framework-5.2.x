package org.springframework.test.context.jdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @SqlMergeMode} is used to annotate a test class or test method to
 * configure whether method-level {@code @Sql} declarations are merged with
 * class-level {@code @Sql} declarations.
 *
 * <p>A method-level {@code @SqlMergeMode} declaration overrides a class-level
 * declaration.
 *
 * <p>If {@code @SqlMergeMode} is not declared on a test class or test method,
 * {@link MergeMode#OVERRIDE} will be used by default.
 *
 * <p>This annotation may be used as a <em>meta-annotation</em> to create custom
 * <em>composed annotations</em> with attribute overrides.
 *
 * @author Sam Brannen
 * @author Dmitry Semukhin
 * @see Sql
 * @see MergeMode#MERGE
 * @see MergeMode#OVERRIDE
 * @since 5.2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SqlMergeMode {

	/**
	 * Indicates whether method-level {@code @Sql} annotations should be merged
	 * with class-level {@code @Sql} annotations or override them.
	 */
	MergeMode value();


	/**
	 * Enumeration of <em>modes</em> that dictate whether method-level {@code @Sql}
	 * declarations are merged with class-level {@code @Sql} declarations.
	 */
	enum MergeMode {

		/**
		 * Indicates that method-level {@code @Sql} declarations should be merged
		 * with class-level {@code @Sql} declarations, with class-level SQL
		 * scripts and statements executed before method-level scripts and
		 * statements.
		 */
		MERGE,

		/**
		 * Indicates that method-level {@code @Sql} declarations should override
		 * class-level {@code @Sql} declarations.
		 */
		OVERRIDE

	}

}
