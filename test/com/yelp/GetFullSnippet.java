/**
 * 
 */
package com.yelp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author neha
 *
 */
public class GetFullSnippet {

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
	 * Test method for {@link com.yelp.SnippetGenerator#getFullSnippet(java.lang.String, int, int)}.
	 */
	@Test
	public void testGetFullSnippet() {
	String expected="ipsum dolor sit amet, consectetur adipiscing elit. " +
		"Phasellus ac mi urna, et pellentesque velit. Quisque ";
	String doc="Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
		"Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit " +
		"amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper " +
		"ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula.";
	assertEquals(expected, SnippetUtil.getFullSnippet(doc, doc.indexOf("ipsum"), doc.indexOf("tincidunt")));
	}

}
