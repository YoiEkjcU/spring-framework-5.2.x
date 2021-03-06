package org.springframework.context.i18n;

import java.util.TimeZone;

import org.springframework.lang.Nullable;

/**
 * Extension of {@link LocaleContext}, adding awareness of the current time zone.
 *
 * <p>Having this variant of LocaleContext set to {@link LocaleContextHolder} means
 * that some TimeZone-aware infrastructure has been configured, even if it may not
 * be able to produce a non-null TimeZone at the moment.
 *
 * @author Juergen Hoeller
 * @author Nicholas Williams
 * @see LocaleContextHolder#getTimeZone()
 * @since 4.0
 */
public interface TimeZoneAwareLocaleContext extends LocaleContext {

	/**
	 * Return the current TimeZone, which can be fixed or determined dynamically,
	 * depending on the implementation strategy.
	 *
	 * @return the current TimeZone, or {@code null} if no specific TimeZone associated
	 */
	@Nullable
	TimeZone getTimeZone();

}
