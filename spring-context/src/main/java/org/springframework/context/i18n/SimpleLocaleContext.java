package org.springframework.context.i18n;

import java.util.Locale;

import org.springframework.lang.Nullable;

/**
 * Simple implementation of the {@link LocaleContext} interface,
 * always returning a specified {@code Locale}.
 *
 * @author Juergen Hoeller
 * @see LocaleContextHolder#setLocaleContext
 * @see LocaleContextHolder#getLocale()
 * @see SimpleTimeZoneAwareLocaleContext
 * @since 1.2
 */
public class SimpleLocaleContext implements LocaleContext {

	@Nullable
	private final Locale locale;


	/**
	 * Create a new SimpleLocaleContext that exposes the specified Locale.
	 * Every {@link #getLocale()} call will return this Locale.
	 *
	 * @param locale the Locale to expose, or {@code null} for no specific one
	 */
	public SimpleLocaleContext(@Nullable Locale locale) {
		this.locale = locale;
	}

	@Override
	@Nullable
	public Locale getLocale() {
		return this.locale;
	}

	@Override
	public String toString() {
		return (this.locale != null ? this.locale.toString() : "-");
	}

}
