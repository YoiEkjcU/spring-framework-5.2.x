package org.springframework.http.client;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpMethod;

/**
 * @author Roy Clarkson
 */
public class OkHttp3ClientHttpRequestFactoryTests extends AbstractHttpRequestFactoryTests {

	@Override
	protected ClientHttpRequestFactory createRequestFactory() {
		return new OkHttp3ClientHttpRequestFactory();
	}

	@Override
	@Test
	public void httpMethods() throws Exception {
		super.httpMethods();
		assertHttpMethod("patch", HttpMethod.PATCH);
	}

}
