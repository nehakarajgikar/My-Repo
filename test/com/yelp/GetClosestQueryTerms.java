/**
 * 
 */
package com.yelp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author neha
 *
 */
public class GetClosestQueryTerms {

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
	 * Test method for {@link com.yelp.SnippetGenerator#getClosestQueryTerms(java.util.Map)}.
	 */
	@Test
	public void testGetClosestQueryTerms() {
		
		Map<String, ArrayList<Integer>> posMap=new HashMap<String, ArrayList<Integer>>();
		
		ArrayList<Integer> aArrayList=new ArrayList<Integer>();
		aArrayList.add(15);
		aArrayList.add(40);
		posMap.put("a", aArrayList);
		
		ArrayList<Integer> bArrayList=new ArrayList<Integer>();
		bArrayList.add(10);
		bArrayList.add(12);
		bArrayList.add(39);
		posMap.put("b", bArrayList);
		
		ArrayList<Integer> cArrayList=new ArrayList<Integer>();
		cArrayList.add(3);
		cArrayList.add(25);
		cArrayList.add(50);
		posMap.put("c", cArrayList);
		
		Map<String, Integer> resPos=new HashMap<String, Integer>();
		resPos.put("a", 40);
		resPos.put("b", 39);
		resPos.put("c", 50);
		
		SnippetGenerator generator=new SnippetGenerator();
		assertEquals(resPos, generator.getClosestQueryTerms(posMap));
	}

}
