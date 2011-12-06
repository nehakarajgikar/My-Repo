package com.yelp;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetSnippet {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public void testGetSnippet(){
		SnippetGenerator generator=new SnippetGenerator();
		String doc="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
				+ " Cras id erat massa. Ullamcorper Lorem Sed ipsum massa risus massa sed id "
				+ "Lorem, ullamcorper nec sollicitudin id, congue sed tortor. Phasellus sed enim leo."
				+ " Nullam vehicula varius faucibus. Vestibulum augue mi, adipiscing ac sagittis ut amet.";
		
		Map<String, Integer> resultPos=new HashMap<String, Integer>();
		resultPos.put("Lorem".toLowerCase(),doc.indexOf("Lorem"));
		resultPos.put("elit", doc.indexOf("elit"));
		resultPos.put("id", doc.indexOf("id"));
		String expected="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
			+ " Cras id erat massa. Ullamcorper Lorem Sed ipsum massa risus massa sed id "
			+ "Lorem, ullamcorper nec sollicitudin id, congue sed tortor. Phasellus";
		assertEquals(expected, generator.getSnippet(doc, resultPos));
	}
	
	@Test
	public void testSnippetAtEndOfDoc(){
		SnippetGenerator generator=new SnippetGenerator();
		String doc="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
				+ " Cras id erat massa. Ullamcorper Lorem Sed ipsum massa risus massa sed id "
				+ "Lorem, ullamcorper nec sollicitudin id, congue sed tortor. Phasellus sed enim leo."
				+ " Nullam vehicula varius faucibus. Vestibulum augue mi, adipiscing ac sagittis ut amet";
		
		Map<String, Integer> resultPos=new HashMap<String, Integer>();
		resultPos.put("Vestibulum".toLowerCase(),doc.indexOf("Vestibulum"));
		resultPos.put("ac", doc.indexOf("ac"));
		resultPos.put("amet", doc.lastIndexOf("amet"));
		
		String expected = "ipsum massa risus massa sed id "
				+ "Lorem, ullamcorper nec sollicitudin id, congue sed tortor. Phasellus sed enim leo."
				+ " Nullam vehicula varius faucibus. Vestibulum augue mi, adipiscing ac sagittis ut amet";
		assertEquals(expected, generator.getSnippet(doc, resultPos));
	}
	
	@Test
	public void testSnippetAtStartOfDoc(){
		SnippetGenerator generator=new SnippetGenerator();
		String doc="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
				+ " Cras id erat massa. Ullamcorper Lorem Sed ipsum massa risus massa sed id "
				+ "Lorem, ullamcorper nec sollicitudin id, congue sed tortor. Phasellus sed enim leo."
				+ " Nullam vehicula varius faucibus. Vestibulum augue mi, adipiscing ac sagittis ut amet";
		
		Map<String, Integer> resultPos=new HashMap<String, Integer>();
		resultPos.put("Lorem".toLowerCase(),doc.indexOf("Lorem"));
		resultPos.put("amet", doc.indexOf("amet"));
		resultPos.put("massa", doc.indexOf("massa"));
		
	
		String expected="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
				+ " Cras id erat massa. Ullamcorper Lorem Sed ipsum massa risus massa sed id "
				+ "Lorem, ullamcorper nec sollicitudin id, congue sed tortor. Phasellus";
		assertEquals(expected, generator.getSnippet(doc, resultPos));
	}
	
	@Test
	public void testLongSnippet(){
		SnippetGenerator generator=new SnippetGenerator();
		String doc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
		"Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit " +
		"amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper " +
		"ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula. " +
		"Sed adipiscing dignissim neque, sit amet iaculis ipsum lacinia non. " +
		"Sed sit amet venenatis nisl. Maecenas id magna velit. Nullam diam elit, " +
		"lacinia eu pulvinar vel, auctor at mi. Integer tortor augue, facilisis at " +
		"pellentesque eget, feugiat sed mi. Suspendisse sed neque mauris. " +
		"Nulla porttitor, neque et commodo accumsan, nisl magna " +
		"lobortis lorem, ac eleifend tellus ipsum in nibh.";
		
		Map<String, Integer> resultPos=new HashMap<String, Integer>();
		resultPos.put("Lorem".toLowerCase(),doc.indexOf("Lorem"));
		resultPos.put("amet", doc.indexOf("amet"));
		resultPos.put("nibh", doc.lastIndexOf("nibh"));
		
	
		String expected="Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
		"Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit " +
		"amet vulputate nulla posuere. Cras condimentum, dolor bibendum";
		assertEquals(expected, generator.getSnippet(doc, resultPos));
	}

	@Test
	public void testUnequalWordsOnEitherSideOfQueryTerms(){
		SnippetGenerator generator=new SnippetGenerator();
		String doc="Lorem ipsum dolor sit amet, consectetur adipiscing elitisting."
				+ " Cras id erat massa. Ullamcorper Lorem Sed ipsum massa risus massa sed id "
				+ "Lorem, ullamcorper nec sollicitudin id, congue sed tortor. Phasellus sed enim leo."
				+ " Nullam vehicula varius faucibus. Vestibulum augue mi, adipiscing ac sagittis ut amet";
		
		Map<String, Integer> resultPos=new HashMap<String, Integer>();
		resultPos.put("elit".toLowerCase(),doc.indexOf("elit"));
		
		
	
		String expected="Lorem ipsum dolor sit amet, consectetur adipiscing elitisting."
				+ " Cras id erat massa. Ullamcorper Lorem Sed ipsum massa risus massa sed id "
				+ "Lorem, ullamcorper nec sollicitudin id, congue sed tortor. Phasellus";
		assertEquals(expected, generator.getSnippet(doc, resultPos));
	}
	
	@Test
	public void testMoreWordsToAppendOnLeftSideOfQueryTerms(){
		SnippetGenerator generator=new SnippetGenerator();
		String doc="Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Phasellus ac mi urna, et pellentesque velit. Quisque tincidunt sem in massa bibendum sit "
				+ "amet vulputate nulla posuere. Cras condimentum, dolor bibendum ullamcorper "
				+ "ornare, metus dui porta arcu, a vehicula nulla ipsum eu ligula. "
				+ "Sed adipiscing dignissim neque, sit amet iaculis ipsum lacinia non. "
				+ "Sed sit amet venenatis nisl. Maecenas id magna velit. Nullam diam elit, "
				+ "lacinia eu pulvinar vel, auctor at mi. Integer tortor augue, facilisis at "
				+ "pellentesque eget, feugiat sed mi. Suspendisse sed neque mauris. "
				+ "Nulla porttitor, neque et commodo accumsan, nisl magna "
				+ "lobortis lorem, ac eleifend tellus ipsum in nibh.";
		
		Map<String, Integer> resultPos=new HashMap<String, Integer>();
		resultPos.put("tellus".toLowerCase(),doc.indexOf("tellus"));
		
		
	
		String expected="Integer tortor augue, facilisis at "
				+ "pellentesque eget, feugiat sed mi. Suspendisse sed neque mauris. "
				+ "Nulla porttitor, neque et commodo accumsan, nisl magna "
				+ "lobortis lorem, ac eleifend tellus ipsum in nibh.";
		assertEquals(expected, generator.getSnippet(doc, resultPos));
	}
}
