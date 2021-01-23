package org.springframework.http.client;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpMethod;

/**
 * @author Roy Clarkson
 */
public class OkHttp3AsyncClientHttpRequestFactoryTests extends AbstractAsyncHttpRequestFactoryTests {

	@SuppressWarnings("deprecation")
	@Override
	protected AsyncClientHttpRequestFactory createRequestFactory() {
		return new OkHttp3ClientHttpRequestFactory();
	}

	@Override
	@Test
	public void httpMethods() throws Exception {
		super.httpMethods();
		assertHttpMethod("patch", HttpMethod.PATCH);
	}

}
