package org.springframework.format.datetime.joda;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;

import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

/**
 * Formats fields annotated with the {@link DateTimeFormat} annotation using Joda-Time.
 *
 * <p><b>NOTE:</b> Spring's Joda-Time support requires Joda-Time 2.x, as of Spring 4.0.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @see DateTimeFormat
 * @since 3.0
 */
public class JodaDateTimeFormatAnnotationFormatterFactory extends EmbeddedValueResolutionSupport
		implements AnnotationFormatterFactory<DateTimeFormat> {

	private static final Set<Class<?>> FIELD_TYPES;

	static {
		// Create the set of field types that may be annotated with @DateTimeFormat.
		// Note: the 3 ReadablePartial concrete types are registered explicitly since
		// addFormatterForFieldType rules exist for each of these types
		// (if we did not do this, the default byType rules for LocalDate, LocalTime,
		// and LocalDateTime would take precedence over the annotation rule, which
		// is not what we want)
		Set<Class<?>> fieldTypes = new HashSet<>(8);
		fieldTypes.add(ReadableInstant.class);
		fieldTypes.add(LocalDate.class);
		fieldTypes.add(LocalTime.class);
		fieldTypes.add(LocalDateTime.class);
		fieldTypes.add(Date.class);
		fieldTypes.add(Calendar.class);
		fieldTypes.add(Long.class);
		FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
	}


	@Override
	public final Set<Class<?>> getFieldTypes() {
		return FIELD_TYPES;
	}

	@Override
	public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
		DateTimeFormatter formatter = getFormatter(annotation, fieldType);
		if (ReadablePartial.class.isAssignableFrom(fieldType)) {
			return new ReadablePartialPrinter(formatter);
		} else if (ReadableInstant.class.isAssignableFrom(fieldType) || Calendar.class.isAssignableFrom(fieldType)) {
			// assumes Calendar->ReadableInstant converter is registered
			return new ReadableInstantPrinter(formatter);
		} else {
			// assumes Date->Long converter is registered
			return new MillisecondInstantPrinter(formatter);
		}
	}

	@Override
	public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
		if (LocalDate.class == fieldType) {
			return new LocalDateParser(getFormatter(annotation, fieldType));
		} else if (LocalTime.class == fieldType) {
			return new LocalTimeParser(getFormatter(annotation, fieldType));
		} else if (LocalDateTime.class == fieldType) {
			return new LocalDateTimeParser(getFormatter(annotation, fieldType));
		} else {
			return new DateTimeParser(getFormatter(annotation, fieldType));
		}
	}

	/**
	 * Factory method used to create a {@link DateTimeFormatter}.
	 *
	 * @param annotation the format annotation for the field
	 * @param fieldType  the type of field
	 * @return a {@link DateTimeFormatter} instance
	 * @since 3.2
	 */
	protected DateTimeFormatter getFormatter(DateTimeFormat annotation, Class<?> fieldType) {
		DateTimeFormatterFactory factory = new DateTimeFormatterFactory();
		String style = resolveEmbeddedValue(annotation.style());
		if (StringUtils.hasLength(style)) {
			factory.setStyle(style);
		}
		factory.setIso(annotation.iso());
		String pattern = resolveEmbeddedValue(annotation.pattern());
		if (StringUtils.hasLength(pattern)) {
			factory.setPattern(pattern);
		}
		return factory.createDateTimeFormatter();
	}

}
