package org.springframework.web.reactive.accept;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.web.testfixture.http.server.reactive.MockServerHttpRequest;
import org.springframework.web.testfixture.server.MockServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link RequestedContentTypeResolverBuilder}.
 * @author Rossen Stoyanchev
 */
public class RequestedContentTypeResolverBuilderTests {

	@Test
	public void defaultSettings() throws Exception {

		RequestedContentTypeResolver resolver = new RequestedContentTypeResolverBuilder().build();
		MockServerWebExchange exchange = MockServerWebExchange.from(
				MockServerHttpRequest.get("/flower").accept(MediaType.IMAGE_GIF));
		List<MediaType> mediaTypes = resolver.resolveMediaTypes(exchange);

		assertThat(mediaTypes).isEqualTo(Collections.singletonList(MediaType.IMAGE_GIF));
	}

	@Test
	public void parameterResolver() throws Exception {

		RequestedContentTypeResolverBuilder builder = new RequestedContentTypeResolverBuilder();
		builder.parameterResolver().mediaType("json", MediaType.APPLICATION_JSON);
		RequestedContentTypeResolver resolver = builder.build();

		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/flower?format=json"));
		List<MediaType> mediaTypes = resolver.resolveMediaTypes(exchange);

		assertThat(mediaTypes).isEqualTo(Collections.singletonList(MediaType.APPLICATION_JSON));
	}

	@Test
	public void parameterResolverWithCustomParamName() throws Exception {

		RequestedContentTypeResolverBuilder builder = new RequestedContentTypeResolverBuilder();
		builder.parameterResolver().mediaType("json", MediaType.APPLICATION_JSON).parameterName("s");
		RequestedContentTypeResolver resolver = builder.build();

		List<MediaType> mediaTypes = resolver.resolveMediaTypes(
				MockServerWebExchange.from(MockServerHttpRequest.get("/flower?s=json")));

		assertThat(mediaTypes).isEqualTo(Collections.singletonList(MediaType.APPLICATION_JSON));
	}

	@Test // SPR-10513
	public void fixedResolver() throws Exception {

		RequestedContentTypeResolverBuilder builder = new RequestedContentTypeResolverBuilder();
		builder.fixedResolver(MediaType.APPLICATION_JSON);
		RequestedContentTypeResolver resolver = builder.build();

		List<MediaType> mediaTypes = resolver.resolveMediaTypes(
				MockServerWebExchange.from(MockServerHttpRequest.get("/").accept(MediaType.ALL)));

		assertThat(mediaTypes).isEqualTo(Collections.singletonList(MediaType.APPLICATION_JSON));
	}

	@Test // SPR-12286
	public void resolver() throws Exception {

		RequestedContentTypeResolverBuilder builder = new RequestedContentTypeResolverBuilder();
		builder.resolver(new FixedContentTypeResolver(MediaType.APPLICATION_JSON));
		RequestedContentTypeResolver resolver = builder.build();

		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/"));
		List<MediaType> mediaTypes = resolver.resolveMediaTypes(exchange);
		assertThat(mediaTypes).isEqualTo(Collections.singletonList(MediaType.APPLICATION_JSON));

		exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").accept(MediaType.ALL));
		mediaTypes = resolver.resolveMediaTypes(exchange);
		assertThat(mediaTypes).isEqualTo(Collections.singletonList(MediaType.APPLICATION_JSON));
	}

}
