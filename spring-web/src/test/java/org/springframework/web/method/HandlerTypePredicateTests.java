package org.springframework.web.method;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link HandlerTypePredicate}.
 * @author Rossen Stoyanchev
 */
public class HandlerTypePredicateTests {

	@Test
	public void forAnnotation() {

		Predicate<Class<?>> predicate = HandlerTypePredicate.forAnnotation(Controller.class);

		assertThat(predicate.test(HtmlController.class)).isTrue();
		assertThat(predicate.test(ApiController.class)).isTrue();
		assertThat(predicate.test(AnotherApiController.class)).isTrue();
	}

	@Test
	public void forAnnotationWithException() {

		Predicate<Class<?>> predicate = HandlerTypePredicate.forAnnotation(Controller.class)
				.and(HandlerTypePredicate.forAssignableType(Special.class));

		assertThat(predicate.test(HtmlController.class)).isFalse();
		assertThat(predicate.test(ApiController.class)).isFalse();
		assertThat(predicate.test(AnotherApiController.class)).isTrue();
	}


	@Controller
	private static class HtmlController {}

	@RestController
	private static class ApiController {}

	@RestController
	private static class AnotherApiController implements Special {}

	interface Special {}

}
