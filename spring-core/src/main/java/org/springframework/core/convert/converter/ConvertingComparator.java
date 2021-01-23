package org.springframework.core.convert.converter;

import java.util.Comparator;
import java.util.Map;

import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.comparator.Comparators;

/**
 * A {@link Comparator} that converts values before they are compared.
 *
 * <p>The specified {@link Converter} will be used to convert each value
 * before it is passed to the underlying {@code Comparator}.
 *
 * @param <S> the source type
 * @param <T> the target type
 * @author Phillip Webb
 * @since 3.2
 */
public class ConvertingComparator<S, T> implements Comparator<S> {

	private final Comparator<T> comparator;

	private final Converter<S, T> converter;


	/**
	 * Create a new {@link ConvertingComparator} instance.
	 *
	 * @param converter the converter
	 */
	public ConvertingComparator(Converter<S, T> converter) {
		this(Comparators.comparable(), converter);
	}

	/**
	 * Create a new {@link ConvertingComparator} instance.
	 *
	 * @param comparator the underlying comparator used to compare the converted values
	 * @param converter  the converter
	 */
	public ConvertingComparator(Comparator<T> comparator, Converter<S, T> converter) {
		Assert.notNull(comparator, "Comparator must not be null");
		Assert.notNull(converter, "Converter must not be null");
		this.comparator = comparator;
		this.converter = converter;
	}

	/**
	 * Create a new {@code ConvertingComparator} instance.
	 *
	 * @param comparator        the underlying comparator
	 * @param conversionService the conversion service
	 * @param targetType        the target type
	 */
	public ConvertingComparator(
			Comparator<T> comparator, ConversionService conversionService, Class<? extends T> targetType) {

		this(comparator, new ConversionServiceConverter<>(conversionService, targetType));
	}


	@Override
	public int compare(S o1, S o2) {
		T c1 = this.converter.convert(o1);
		T c2 = this.converter.convert(o2);
		return this.comparator.compare(c1, c2);
	}

	/**
	 * Create a new {@link ConvertingComparator} that compares {@linkplain java.util.Map.Entry
	 * map entries} based on their {@linkplain java.util.Map.Entry#getKey() keys}.
	 *
	 * @param comparator the underlying comparator used to compare keys
	 * @return a new {@link ConvertingComparator} instance
	 */
	public static <K, V> ConvertingComparator<Map.Entry<K, V>, K> mapEntryKeys(Comparator<K> comparator) {
		return new ConvertingComparator<>(comparator, Map.Entry::getKey);
	}

	/**
	 * Create a new {@link ConvertingComparator} that compares {@linkplain java.util.Map.Entry
	 * map entries} based on their {@linkplain java.util.Map.Entry#getValue() values}.
	 *
	 * @param comparator the underlying comparator used to compare values
	 * @return a new {@link ConvertingComparator} instance
	 */
	public static <K, V> ConvertingComparator<Map.Entry<K, V>, V> mapEntryValues(Comparator<V> comparator) {
		return new ConvertingComparator<>(comparator, Map.Entry::getValue);
	}


	/**
	 * Adapts a {@link ConversionService} and <tt>targetType</tt> to a {@link Converter}.
	 */
	private static class ConversionServiceConverter<S, T> implements Converter<S, T> {

		private final ConversionService conversionService;

		private final Class<? extends T> targetType;

		public ConversionServiceConverter(ConversionService conversionService, Class<? extends T> targetType) {
			Assert.notNull(conversionService, "ConversionService must not be null");
			Assert.notNull(targetType, "TargetType must not be null");
			this.conversionService = conversionService;
			this.targetType = targetType;
		}

		@Override
		@Nullable
		public T convert(S source) {
			return this.conversionService.convert(source, this.targetType);
		}
	}

}
