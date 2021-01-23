package org.springframework.expression.spel;

import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.expression.PropertyAccessor;

/**
 * A compilable property accessor is able to generate bytecode that represents
 * the access operation, facilitating compilation to bytecode of expressions
 * that use the accessor.
 *
 * @author Andy Clement
 * @since 4.1
 */
public interface CompilablePropertyAccessor extends PropertyAccessor, Opcodes {

	/**
	 * Return {@code true} if this property accessor is currently suitable for compilation.
	 */
	boolean isCompilable();

	/**
	 * Return the type of the accessed property - may only be known once an access has occurred.
	 */
	Class<?> getPropertyType();

	/**
	 * Generate the bytecode the performs the access operation into the specified MethodVisitor
	 * using context information from the codeflow where necessary.
	 *
	 * @param propertyName the name of the property
	 * @param mv           the Asm method visitor into which code should be generated
	 * @param cf           the current state of the expression compiler
	 */
	void generateCode(String propertyName, MethodVisitor mv, CodeFlow cf);

}
