package org.springframework.core.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * {@link CommandLinePropertySource} implementation backed by a JOpt {@link OptionSet}.
 *
 * <h2>Typical usage</h2>
 * <p>
 * Configure and execute an {@code OptionParser} against the {@code String[]} of arguments
 * supplied to the {@code main} method, and create a {@link JOptCommandLinePropertySource}
 * using the resulting {@code OptionSet} object:
 *
 * <pre class="code">
 * public static void main(String[] args) {
 *     OptionParser parser = new OptionParser();
 *     parser.accepts("option1");
 *     parser.accepts("option2").withRequiredArg();
 *     OptionSet options = parser.parse(args);
 *     PropertySource<?> ps = new JOptCommandLinePropertySource(options);
 *     // ...
 * }</pre>
 * <p>
 * See {@link CommandLinePropertySource} for complete general usage examples.
 *
 * <p>Requires JOpt Simple version 4.3 or higher. Tested against JOpt up until 5.0.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @author Dave Syer
 * @see CommandLinePropertySource
 * @see joptsimple.OptionParser
 * @see joptsimple.OptionSet
 * @since 3.1
 */
public class JOptCommandLinePropertySource extends CommandLinePropertySource<OptionSet> {

	/**
	 * Create a new {@code JOptCommandLinePropertySource} having the default name
	 * and backed by the given {@code OptionSet}.
	 *
	 * @see CommandLinePropertySource#COMMAND_LINE_PROPERTY_SOURCE_NAME
	 * @see CommandLinePropertySource#CommandLinePropertySource(Object)
	 */
	public JOptCommandLinePropertySource(OptionSet options) {
		super(options);
	}

	/**
	 * Create a new {@code JOptCommandLinePropertySource} having the given name
	 * and backed by the given {@code OptionSet}.
	 */
	public JOptCommandLinePropertySource(String name, OptionSet options) {
		super(name, options);
	}


	@Override
	protected boolean containsOption(String name) {
		return this.source.has(name);
	}

	@Override
	public String[] getPropertyNames() {
		List<String> names = new ArrayList<>();
		for (OptionSpec<?> spec : this.source.specs()) {
			String lastOption = CollectionUtils.lastElement(spec.options());
			if (lastOption != null) {
				// Only the longest name is used for enumerating
				names.add(lastOption);
			}
		}
		return StringUtils.toStringArray(names);
	}

	@Override
	@Nullable
	public List<String> getOptionValues(String name) {
		List<?> argValues = this.source.valuesOf(name);
		List<String> stringArgValues = new ArrayList<>();
		for (Object argValue : argValues) {
			stringArgValues.add(argValue.toString());
		}
		if (stringArgValues.isEmpty()) {
			return (this.source.has(name) ? Collections.emptyList() : null);
		}
		return Collections.unmodifiableList(stringArgValues);
	}

	@Override
	protected List<String> getNonOptionArgs() {
		List<?> argValues = this.source.nonOptionArguments();
		List<String> stringArgValues = new ArrayList<>();
		for (Object argValue : argValues) {
			stringArgValues.add(argValue.toString());
		}
		return (stringArgValues.isEmpty() ? Collections.emptyList() :
				Collections.unmodifiableList(stringArgValues));
	}

}
