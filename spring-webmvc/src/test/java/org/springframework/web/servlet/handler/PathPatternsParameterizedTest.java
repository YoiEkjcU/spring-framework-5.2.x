package org.springframework.web.servlet.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for tests parameterized to use either
 * {@link org.springframework.web.util.pattern.PathPatternParser} or
 * {@link org.springframework.util.PathMatcher} for URL pattern matching.
 *
 * @author Rossen Stoyanchev
 * @since 5.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@org.junit.jupiter.params.ParameterizedTest
@org.junit.jupiter.params.provider.MethodSource("pathPatternsArguments")
public @interface PathPatternsParameterizedTest {
}
