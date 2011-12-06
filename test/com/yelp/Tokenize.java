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
public class Tokenize {

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
	 * Test method for {@link com.yelp.SnippetGenerator#tokenize(java.lang.String)}.
	 */
	@Test
	public void testTokenize() {
		assertArrayEquals(new String[] { "small", "and", "big" },
				SnippetUtil.tokenize(" small and big"));

	}

}
