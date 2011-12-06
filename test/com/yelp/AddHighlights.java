/**
 * 
 */
package com.yelp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author neha
 *
 */
public class AddHighlights {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.yelp.SnippetGenerator#addHighlights(java.lang.String[], java.lang.String)}.
	 * Check if the highlight is done correctly provided the search terms and the snippet.
	 */
	@Test
	public void testAddHighlights() {
		SnippetGenerator generator=new SnippetGenerator();
		
		String snippet="Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
		"Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit " +
		"amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper " +
		"ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula.";
		String[] queryTokens=new String[]{"lorem","amet", "ullamcorper","bibendum"};
		
		assertEquals("<HIGHLIGHT>Lorem</HIGHLIGHT> ipsum dolor sit <HIGHLIGHT>amet</HIGHLIGHT>, consectetur adipiscing elit. " +
		"Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa <HIGHLIGHT>bibendum</HIGHLIGHT> sit " +
		"<HIGHLIGHT>amet</HIGHLIGHT> vulputate nulla posuere. Cras condimentum, dolor <HIGHLIGHT>bibendum</HIGHLIGHT> <HIGHLIGHT>ullamcorper</HIGHLIGHT> " +
		"ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula.", generator.addHighlights(queryTokens, snippet));
	}
	
	@Test
	public void testAddHighlightsForSubstrings() {
		SnippetGenerator generator=new SnippetGenerator();
		
		String snippet="Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
		"Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit " +
		"amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper " +
		"ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula.";
		String[] queryTokens=new String[]{"bib"};
		
		assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
		"Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa <HIGHLIGHT>bib</HIGHLIGHT>endum sit " +
		"amet vulputate nulla posuere. Cras condimentum, dolor <HIGHLIGHT>bib</HIGHLIGHT>endum ullamcorper " +
		"ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula.", generator.addHighlights(queryTokens, snippet));
	}

}
