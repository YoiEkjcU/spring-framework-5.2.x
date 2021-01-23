package org.springframework.asm;

/**
 * Utility class exposing constants related to Spring's internal repackaging
 * of the ASM bytecode library: currently based on ASM 9.0 plus minor patches.
 *
 * <p>See <a href="package-summary.html">package-level javadocs</a> for more
 * information on {@code org.springframework.asm}.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.2
 */
public final class SpringAsmInfo {

	/**
	 * The ASM compatibility version for Spring's ASM visitor implementations:
	 * currently {@link Opcodes#ASM10_EXPERIMENTAL}, as of Spring Framework 5.3.
	 */
	public static final int ASM_VERSION = Opcodes.ASM10_EXPERIMENTAL;

}
