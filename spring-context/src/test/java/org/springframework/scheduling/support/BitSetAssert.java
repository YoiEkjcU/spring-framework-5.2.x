package org.springframework.scheduling.support;

import java.util.BitSet;

import org.assertj.core.api.AbstractAssert;

/**
 * @author Arjen Poutsma
 */
public class BitSetAssert extends AbstractAssert<BitSetAssert, BitSet> {

	private BitSetAssert(BitSet bitSet) {
		super(bitSet, BitSetAssert.class);
	}

	public static BitSetAssert assertThat(BitSet actual) {
		return new BitSetAssert(actual);
	}

	public BitSetAssert hasSet(int... indices) {
		isNotNull();

		for (int index : indices) {
			if (!this.actual.get(index)) {
				failWithMessage("Invalid disabled bit at @%d", index);
			}
		}
		return this;
	}

	public BitSetAssert hasSetRange(int min, int max) {
		isNotNull();

		for (int i = min; i < max; i++) {
			if (!this.actual.get(i)) {
				failWithMessage("Invalid disabled bit at @%d", i);
			}
		}
		return this;
	}

	public BitSetAssert hasUnset(int... indices) {
		isNotNull();

		for (int index : indices) {
			if (this.actual.get(index)) {
				failWithMessage("Invalid enabled bit at @%d", index);
			}
		}
		return this;
	}

	public BitSetAssert hasUnsetRange(int min, int max) {
		isNotNull();

		for (int i = min; i < max; i++) {
			if (this.actual.get(i)) {
				failWithMessage("Invalid enabled bit at @%d", i);
			}
		}
		return this;
	}

}

