package org.springframework.r2dbc.core.binding;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link AnonymousBindMarkers}.
 *
 * @author Mark Paluch
 */
class AnonymousBindMarkersUnitTests {

	@Test
	public void shouldCreateNewBindMarkers() {
		BindMarkersFactory factory = BindMarkersFactory.anonymous("?");

		BindMarkers bindMarkers1 = factory.create();
		BindMarkers bindMarkers2 = factory.create();

		assertThat(bindMarkers1.next().getPlaceholder()).isEqualTo("?");
		assertThat(bindMarkers2.next().getPlaceholder()).isEqualTo("?");
	}

	@Test
	public void shouldBindByIndex() {
		BindTarget bindTarget = mock(BindTarget.class);

		BindMarkers bindMarkers = BindMarkersFactory.anonymous("?").create();

		BindMarker first = bindMarkers.next();
		BindMarker second = bindMarkers.next();

		second.bind(bindTarget, "foo");
		first.bindNull(bindTarget, Object.class);

		verify(bindTarget).bindNull(0, Object.class);
		verify(bindTarget).bind(1, "foo");
	}

}
