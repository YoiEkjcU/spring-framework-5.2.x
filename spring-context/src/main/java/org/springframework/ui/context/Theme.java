package org.springframework.ui.context;

import org.springframework.context.MessageSource;

/**
 * A Theme can resolve theme-specific messages, codes, file paths, etcetera
 * (e&#46;g&#46; CSS and image files in a web environment).
 * The exposed {@link org.springframework.context.MessageSource} supports
 * theme-specific parameterization and internationalization.
 *
 * @author Juergen Hoeller
 * @see ThemeSource
 * @see org.springframework.web.servlet.ThemeResolver
 * @since 17.06.2003
 */
public interface Theme {

	/**
	 * Return the name of the theme.
	 *
	 * @return the name of the theme (never {@code null})
	 */
	String getName();

	/**
	 * Return the specific MessageSource that resolves messages
	 * with respect to this theme.
	 *
	 * @return the theme-specific MessageSource (never {@code null})
	 */
	MessageSource getMessageSource();

}
