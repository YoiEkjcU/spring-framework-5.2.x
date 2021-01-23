package org.springframework.util.xml;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.xmlunit.util.Predicate;

import org.springframework.core.testfixture.xml.XmlContent;

import static org.assertj.core.api.Assertions.assertThat;

class XMLEventStreamReaderTests {

	private static final String XML =
			"<?pi content?><root xmlns='namespace'><prefix:child xmlns:prefix='namespace2'>content</prefix:child></root>"
			;

	private XMLEventStreamReader streamReader;

	@BeforeEach
	void createStreamReader() throws Exception {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = inputFactory.createXMLEventReader(new StringReader(XML));
		streamReader = new XMLEventStreamReader(eventReader);
	}

	@Test
	void readAll() throws Exception {
		while (streamReader.hasNext()) {
			streamReader.next();
		}
	}

	@Test
	void readCorrect() throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StAXSource source = new StAXSource(streamReader);
		StringWriter writer = new StringWriter();
		transformer.transform(source, new StreamResult(writer));
		Predicate<Node> nodeFilter = n ->
				n.getNodeType() != Node.DOCUMENT_TYPE_NODE && n.getNodeType() != Node.PROCESSING_INSTRUCTION_NODE;
		assertThat(XmlContent.from(writer)).isSimilarTo(XML, nodeFilter);
	}

}
