package org.springframework.jmx.export.annotation;

/**
 * @author Rob Harrop
 */
public interface IAnnotationTestBean {

	@ManagedAttribute
	String getColour();

	@ManagedAttribute
	void setColour(String colour);

	@ManagedOperation
	void fromInterface();

	@ManagedOperation
	int getExpensiveToCalculate();
}
