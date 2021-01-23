package org.springframework.http.client;


public class NoOutputStreamingStreamingSimpleHttpRequestFactoryTests extends AbstractHttpRequestFactoryTests {

	@Override
	protected ClientHttpRequestFactory createRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setBufferRequestBody(false);
		factory.setOutputStreaming(false);
		return factory;
	}
}
