package org.springframework.expression.spel.support;

import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelMessage;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Default implementation of the {@link TypeConverter} interface,
 * delegating to a core Spring {@link ConversionService}.
 *
 * @author Juergen Hoeller
 * @author Andy Clement
 * @see org.springframework.core.convert.ConversionService
 * @since 3.0
 */
public class StandardTypeConverter implements TypeConverter {

	private final ConversionService conversionService;


	/**
	 * Create a StandardTypeConverter for the default ConversionService.
	 *
	 * @see DefaultConversionService#getSharedInstance()
	 */
	public StandardTypeConverter() {
		this.conversionService = DefaultConversionService.getSharedInstance();
	}

	/**
	 * Create a StandardTypeConverter for the given ConversionService.
	 *
	 * @param conversionService the ConversionService to delegate to
	 */
	public StandardTypeConverter(ConversionService conversionService) {
		Assert.notNull(conversionService, "ConversionService must not be null");
		this.conversionService = conversionService;
	}


	@Override
	public boolean canConvert(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
		return this.conversionService.canConvert(sourceType, targetType);
	}

	@Override
	@Nullable
	public Object convertValue(@Nullable Object value, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			return this.conversionService.convert(value, sourceType, targetType);
		} catch (ConversionException ex) {
			throw new SpelEvaluationException(ex, SpelMessage.TYPE_CONVERSION_ERROR,
					(sourceType != null ? sourceType.toString() : (value != null ? value.getClass().getName() : "null")),
					targetType.toString());
		}
	}

}
