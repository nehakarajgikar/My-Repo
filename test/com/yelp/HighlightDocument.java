package com.yelp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HighlightDocument {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * When the doc length is less than snippet length, display everything
	 */
	@Test
	public void testHighlightDocumentShort() {
		String doc = "My name is neha. Please search for query terms within a short phrase.";
		String query = "neha short";
		SnippetGenerator generator = new SnippetGenerator();
		assertEquals(
				"My name is <HIGHLIGHT>neha</HIGHLIGHT>. Please search for query terms within a <HIGHLIGHT>short</HIGHLIGHT> phrase.",
				generator.highlightDocument(doc, query));
	}

	@Test
	public void testHighlightLongDocument() {
		String doc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit "
				+ "amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper "
				+ "ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula. "
				+ "Sed adipiscing dignissim neque, sit amet iaculis ipsum lacinia non. "
				+ "Sed sit amet venenatis nisl. Maecenas id magna velit. Nullam diam elit, "
				+ "lacinia eu pulvinar vel, auctor at mi. Integer tortor augue, facilisis at "
				+ "pellentesque eget, feugiat sed mi. Suspendisse sed neque mauris. "
				+ "Nulla porttitor, neque et commodo accumsan, nisl magna "
				+ "lobortis lorem, ac eleifend tellus ipsum in nibh.";
		String query = "maecenas sed mi";
		String expected = "amet iaculis ipsum lacinia non. "
				+ "<HIGHLIGHT>Sed</HIGHLIGHT> sit amet venenatis nisl. <HIGHLIGHT>Maecenas</HIGHLIGHT> id magna velit. Nullam diam elit, "
				+ "lacinia eu pulvinar vel, auctor at <HIGHLIGHT>mi</HIGHLIGHT>. Integer tortor augue, facilisis at "
				+ "pellentesque";

		SnippetGenerator generator = new SnippetGenerator();
		assertEquals(expected, generator.highlightDocument(doc, query));
	}

	@Test
	public void testQueryTermAtBeginningOfDocument() {
		String doc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit "
				+ "amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper "
				+ "ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula. "
				+ "Sed adipiscing dignissim neque, sit amet iaculis ipsum lacinia non. "
				+ "Sed sit amet venenatis nisl. Maecenas id magna velit. Nullam diam elit, "
				+ "lacinia eu pulvinar vel, auctor at mi. Integer tortor augue, facilisis at "
				+ "pellentesque eget, feugiat sed mi. Suspendisse sed neque mauris. "
				+ "Nulla porttitor, neque et commodo accumsan, nisl magna "
				+ "lobortis lorem, ac eleifend tellus ipsum in nibh.";
		String query = "Lorem phasellus venenatis";
		String expected = "<HIGHLIGHT>Lorem</HIGHLIGHT> ipsum dolor sit amet, consectetur adipiscing "
				+ "elit. <HIGHLIGHT>Phasellus</HIGHLIGHT> ac mi urna, et pellentesque velit. "
				+ "Quisque tincidunt sem in massa bibendum sit amet vulputate nulla posuere. "
				+ "Cras condimentum, dolor bibendum";
		SnippetGenerator generator = new SnippetGenerator();
		assertEquals(expected, generator.highlightDocument(doc, query));
	}

	@Test
	public void testQueryTermAtEndOfDocument() {
		String doc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit "
				+ "amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper "
				+ "ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula. "
				+ "Sed adipiscing dignissim neque, sit amet iaculis ipsum lacinia non. "
				+ "Sed sit amet venenatis nisl. Maecenas id magna velit. Nullam diam elit, "
				+ "lacinia eu pulvinar vel, auctor at mi. Integer tortor augue, facilisis at "
				+ "pellentesque eget, feugiat sed mi. Suspendisse sed neque mauris. "
				+ "Nulla porttitor, neque et commodo accumsan, nisl magna "
				+ "lobortis lorem, ac eleifend tellus ipsum in nibh.";
		String query = "neque nibh";
		String expected = "Integer tortor augue, facilisis at "
				+ "pellentesque eget, feugiat sed mi. Suspendisse sed <HIGHLIGHT>neque</HIGHLIGHT> mauris. "
				+ "Nulla porttitor, <HIGHLIGHT>neque</HIGHLIGHT> et commodo accumsan, nisl magna "
				+ "lobortis lorem, ac eleifend tellus ipsum in <HIGHLIGHT>nibh</HIGHLIGHT>.";
		SnippetGenerator generator = new SnippetGenerator();

		assertEquals(expected, generator.highlightDocument(doc, query));
		System.out.println(SnippetUtil.tokenize(generator.highlightDocument(doc,
				query)).length);
	}

	@Test
	public void testSingleQueryTerm() {
		String doc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit "
				+ "amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper "
				+ "ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula. "
				+ "Sed adipiscing dignissim neque, sit amet iaculis ipsum lacinia non. "
				+ "Sed sit amet venenatis nisl. Maecenas id magna velit. Nullam diam elit, "
				+ "lacinia eu pulvinar vel, auctor at mi. Integer tortor augue, facilisis at "
				+ "pellentesque eget, feugiat sed mi. Suspendisse sed neque mauris. "
				+ "Nulla porttitor, neque et commodo accumsan, nisl magna "
				+ "lobortis lorem, ac eleifend tellus ipsum in nibh.";
		String query = "ipsum";
		String expected = "Lorem <HIGHLIGHT>ipsum</HIGHLIGHT> dolor sit amet, consectetur adipiscing elit. "
				+ "Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit "
				+ "amet vulputate nulla posuere. Cras condimentum, dolor bibendum";
		SnippetGenerator generator = new SnippetGenerator();

		assertEquals(expected, generator.highlightDocument(doc, query));
		System.out.println(SnippetUtil.tokenize(generator.highlightDocument(doc,
				query)).length);
	}

	@Test
	public void testWithoutMatchingQueryTerm() {
		String doc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit "
				+ "amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper "
				+ "ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula. "
				+ "Sed adipiscing dignissim neque, sit amet iaculis ipsum lacinia non. "
				+ "Sed sit amet venenatis nisl. Maecenas id magna velit. Nullam diam elit, "
				+ "lacinia eu pulvinar vel, auctor at mi. Integer tortor augue, facilisis at "
				+ "pellentesque eget, feugiat sed mi. Suspendisse sed neque mauris. "
				+ "Nulla porttitor, neque et commodo accumsan, nisl magna "
				+ "lobortis lorem, ac eleifend tellus ipsum in nibh.";
		String query = "neha";
		String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit "
				+ "amet vulputate nulla posuere. Cras condimentum, dolor bibendum";
		SnippetGenerator generator = new SnippetGenerator();
		assertEquals(expected, generator.highlightDocument(doc, query));
		System.out.println(SnippetUtil.tokenize(generator.highlightDocument(doc,
				query)).length);
	}

}
