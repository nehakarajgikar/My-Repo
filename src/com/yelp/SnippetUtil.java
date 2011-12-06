/**
 * 
 */
package com.yelp;

/**
 * @author neha
 *
 */
public class SnippetUtil {
	
	
	public static String getLeftWords(String doc, int leftWordCount, int i) {
		int j = doc.length();
		while (leftWordCount != 0) {
			i = doc.lastIndexOf(' ', i - 1);
			leftWordCount--;
			if (i == -1) {
				i = 0;
				leftWordCount = 0;
				break;
			}
		}
		String snippet = doc.substring(i, j);
		System.out.println("snippet from end of doc: " + doc.substring(i, j));
		return snippet;
	}
	
	public static String getRightWords(String doc, int rightWordCount, int j) {
		int i = 0;
		while (rightWordCount != 0) {
			j = doc.indexOf(' ', j + 1);
			rightWordCount--;
			if (j == doc.length() || j < 0) {
				rightWordCount = 0;
				j = doc.length();
				break;
			}
		}
		String snippet  = doc.substring(i, j);
		System.out.println("snippet from start of doc: " + doc.substring(i, j));
		return snippet;
	}
	
	public static String getLeftAndRightWords(String doc, int leftWordCount,
			int rightWordCount, int i, int j) {
		
		while (rightWordCount != 0) {
			j = doc.indexOf(' ', j + 1);
			rightWordCount--;
			if (j == doc.length() || j < 0) {
				rightWordCount = 0;
				j = doc.length();
				break;
			}
		}
		while (leftWordCount != 0) {
			i = doc.lastIndexOf(' ', i - 1);
			leftWordCount--;
			if (i == -1) {
				i = 0;
				leftWordCount = 0;
				break;
			}
		}
		String snippet = doc.substring(i, j);
		return snippet;
	}
	
	public static SnippetType getSnippetType(int leftWordCount, int rightWordCount) {
		if (leftWordCount != 0 && rightWordCount != 0)
			return SnippetType.SNIPPET_FROM_MIDDLE;
		else if (leftWordCount == 0)
			return SnippetType.SNIPPET_FROM_START_OF_DOC;
		else
			return SnippetType.SNIPPET_WITH_END_OF_DOC;
	}
	public static int leftWordsToAppend(String doc, int start, int leftWordCount) {
		if (start <= 0) {
			return 0;
		}
		int i = 0;
		for (; i < leftWordCount; i++) {
			if ((start = doc.lastIndexOf(' ', start - 1)) == -1) {
				break;
			}
		}
		return i;

	}

	public static int rightWordsToAppend(String doc, int start, int rightWordCount) {
		if (start >= doc.length()) {
			return 0;
		}
		int i = 1;
		for (; i <= rightWordCount; i++) {
			if ((start = doc.indexOf(' ', start + 1)) == -1) {
				break;
			}
		}
		return i;

	}
	
	/**
	 * Method to get the position of the first whitespace after a word ends.
	 * 
	 * @param str
	 * @param start
	 * @return
	 */
	public static int getWhitespaceAfterWord(String str, Integer start) {
		// System.out.println(str.charAt(start));
		if (str.indexOf(' ', start) == -1)
			return str.length();
		return str.indexOf(' ', start);
		// return str.indexOf(" ", start);

	}
	
	/**
	 * Splits the string based on whitespaces. Also trims unnecessary
	 * whitespaces in the document
	 * 
	 * @param str
	 * @return
	 */
	public static String[] tokenize(String str) {
		return str.trim().split("\\s");
	}

	/**
	 * Get the snippet of the document that lies between the smallest and the
	 * biggest indices provided.
	 * 
	 * @param doc
	 * @param smallest
	 * @param biggest
	 * @return
	 */
	public static String getFullSnippet(String doc, int smallest, int biggest) {
		char[] dst = new char[biggest - smallest];
		doc.getChars(smallest, biggest, dst, 0);

		return new String(dst);
	}
}
