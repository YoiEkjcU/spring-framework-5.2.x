package org.springframework.jmx.export.metadata;

/**
 * Metadata that indicates to expose a given method as JMX operation.
 * Only valid when used on a method that is not a JavaBean getter or setter.
 *
 * @author Rob Harrop
 * @see org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler
 * @see org.springframework.jmx.export.MBeanExporter
 * @since 1.2
 */
public class ManagedOperation extends AbstractJmxAttribute {

}
