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
public class GetWhitespaceAfterWord {

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
	 * Test method for
	 * {@link com.yelp.SnippetGenerator#getWhitespaceAfterWord(java.lang.String, java.lang.Integer)}
	 * .
	 */
	@Test
	public void testGetWhitespaceAfterWord() {
		String words = "My name is neha";
		assertEquals(7, SnippetUtil.getWhitespaceAfterWord(words,
				words.indexOf("name")));
	}

	/**
	 * Test method for
	 * {@link com.yelp.SnippetGenerator#getWhitespaceAfterWord(java.lang.String, java.lang.Integer)}
	 * .
	 */
	@Test
	public void testGetWhitespaceAfterLastWord() {
		String words = "My name is neha";
		assertEquals(words.length(), SnippetUtil.getWhitespaceAfterWord(words,
				words.indexOf("neha")));
	}

}
