package org.springframework.core.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.lang.Nullable;

/**
 * A simple representation of command line arguments, broken into "option arguments" and
 * "non-option arguments".
 *
 * @author Chris Beams
 * @see SimpleCommandLineArgsParser
 * @since 3.1
 */
class CommandLineArgs {

	private final Map<String, List<String>> optionArgs = new HashMap<>();
	private final List<String> nonOptionArgs = new ArrayList<>();

	/**
	 * Add an option argument for the given option name and add the given value to the
	 * list of values associated with this option (of which there may be zero or more).
	 * The given value may be {@code null}, indicating that the option was specified
	 * without an associated value (e.g. "--foo" vs. "--foo=bar").
	 */
	public void addOptionArg(String optionName, @Nullable String optionValue) {
		if (!this.optionArgs.containsKey(optionName)) {
			this.optionArgs.put(optionName, new ArrayList<>());
		}
		if (optionValue != null) {
			this.optionArgs.get(optionName).add(optionValue);
		}
	}

	/**
	 * Return the set of all option arguments present on the command line.
	 */
	public Set<String> getOptionNames() {
		return Collections.unmodifiableSet(this.optionArgs.keySet());
	}

	/**
	 * Return whether the option with the given name was present on the command line.
	 */
	public boolean containsOption(String optionName) {
		return this.optionArgs.containsKey(optionName);
	}

	/**
	 * Return the list of values associated with the given option. {@code null} signifies
	 * that the option was not present; empty list signifies that no values were associated
	 * with this option.
	 */
	@Nullable
	public List<String> getOptionValues(String optionName) {
		return this.optionArgs.get(optionName);
	}

	/**
	 * Add the given value to the list of non-option arguments.
	 */
	public void addNonOptionArg(String value) {
		this.nonOptionArgs.add(value);
	}

	/**
	 * Return the list of non-option arguments specified on the command line.
	 */
	public List<String> getNonOptionArgs() {
		return Collections.unmodifiableList(this.nonOptionArgs);
	}

}
