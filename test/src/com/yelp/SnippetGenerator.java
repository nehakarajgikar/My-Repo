package com.yelp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class - entry point to the highlightDocument method
 * 
 * @author neha
 * 
 */
public class SnippetGenerator {

	private static final int NO_INDEX = -1;
	private static final int END_OF_LIST = -2;
	private static final int SNIPPET_LENGTH = 30;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("../Yelp/doc/doc.txt"));
		} catch (FileNotFoundException e) {
			System.err
					.println("File is not found: please enter correct file name and rerun.");
			return;
		}

		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {

		}
		Scanner sc = new Scanner(System.in);
		System.out.println("enter search terms: ");
		String search = sc.nextLine();
		SnippetGenerator generator = new SnippetGenerator();
		String snippet = generator.highlightDocument(sb.toString(), search);
		System.out.println(snippet);

	}

	/**
	 * Primary method for returning the highlighted snippet.
	 * 
	 * @param doc
	 *            - the document input
	 * @param query
	 *            - the search terms
	 * @return - highlighted snippet
	 */
	public String highlightDocument(String doc, String query) {
		String snippet = null;

		// if the document is short, then display entire document
		if (SnippetUtil.tokenize(doc).length <= SNIPPET_LENGTH) {
			snippet = addHighlights(SnippetUtil.tokenize(query), doc);
			return snippet;
		}

		// create regex pattern and insert into map - the query terms as well as
		// their positions in an array list
		Map<String, ArrayList<Integer>> positionMap = populateMap(doc,
				SnippetUtil.tokenize(query.toLowerCase()));
		String[] queryTokens = positionMap.keySet().toArray(
				new String[positionMap.keySet().size()]);
		// check if the search terms were found at all in the document.
		// if not found, then return first part of document equal to
		// SNIPPET_LENGTH
		if (queryTokens.length == 0) {
			snippet = getSnippet(doc);
			System.out.println("snippet: " + snippet);
			return snippet;
		}

		// map to hold the result of the closest query terms
		Map<String, Integer> resultPos = getClosestQueryTerms(positionMap);

		// now determine what to display and do the highlighting

		snippet = getSnippet(doc, resultPos);

		snippet = addHighlights(queryTokens, snippet);

		System.out.println("snippet: " + snippet);
		return snippet;
	}

	private String getSnippet(String doc) {

		int posOfSpace = -1;
		for (int i = 0; i < SNIPPET_LENGTH; i++) {
			posOfSpace = doc.indexOf(' ', posOfSpace + 1);
		}
		return doc.substring(0, posOfSpace).trim();

	}

	/**
	 * Get the closest query terms in the document and return them in resultPos.
	 * We define closest as the words that have the least distance between each
	 * other.
	 * 
	 * @param positionMap
	 * @return
	 */
	public Map<String, Integer> getClosestQueryTerms(
			Map<String, ArrayList<Integer>> positionMap) {

		// query tokens are only the search terms that are found in the given
		// document
		String[] queryTokens = (String[]) positionMap.keySet().toArray(
				new String[positionMap.keySet().size()]);

		// initializing the map to hold current index, and map to hold the final
		// result positions of query terms in most relevant snippet
		Map<String, Integer> currIndexMap = new HashMap<String, Integer>();
		Map<String, Integer> resultPos = new HashMap<String, Integer>();
		for (int i = 0; i < queryTokens.length; i++) {
			currIndexMap.put(queryTokens[i], 0);
			resultPos.put(queryTokens[i], NO_INDEX);
		}
		// here we are going to maintain the minimum difference between the
		// starting positions of all words.

		int minDiff = Integer.MAX_VALUE;
		while (true) {
			String minKey = null;

			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			// find the minimum difference and minimum key that needs to
			// incremented next
			for (Iterator iterator = currIndexMap.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				// if (currIndexMap.get(key) == END_OF_LIST) {
				// continue;
				// }
				if (positionMap.get(key).get(currIndexMap.get(key)) < min) {
					min = positionMap.get(key).get(currIndexMap.get(key));
					minKey = key;
				}
				if (positionMap.get(key).get(currIndexMap.get(key)) > max) {
					max = positionMap.get(key).get(currIndexMap.get(key));
				}

			}

			int curDiff = max - min;
			// System.out.println("curr diff: " + curDiff);

			// System.out.println("min key: " + minKey);
			// check if current difference is less than the last min difference
			if (curDiff < minDiff) {
				minDiff = curDiff;
				for (Iterator iterator = resultPos.keySet().iterator(); iterator
						.hasNext();) {
					String key = (String) iterator.next();
					resultPos.put(key,
							positionMap.get(key).get(currIndexMap.get(key)));
				}
				// System.out.println("updated result pos: " + resultPos);
			}

			// advance the minKey index. if no more keys are found, then insert
			// position as end of list
			if ((currIndexMap.get(minKey) + 1) == positionMap.get(minKey)
					.size()
					|| positionMap.get(minKey)
							.get(currIndexMap.get(minKey) + 1) == null) {
				currIndexMap.put(minKey, END_OF_LIST);
				// break;
			} else {
				currIndexMap.put(minKey, currIndexMap.get(minKey) + 1);
			}

			// if any values in currIndexMap are marked as END_OF_LIST, break
			// out of the while loop
			boolean isOver = false;
			for (Iterator iterator = currIndexMap.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				if (currIndexMap.get(key) == END_OF_LIST) {
					isOver = true;
					break;
				}
			}
			if (isOver) {
				break;
			}
		}
		return resultPos;
	}

	/**
	 * Method where we retrieve the actual words from the document that
	 * correspond to the closest query terms. SNIPPET_LENGTH is used to
	 * determine how many more words from the document need to be added to the
	 * snippet
	 * 
	 * @param doc
	 * @param resultPos
	 * @return
	 */
	public String getSnippet(String doc, Map<String, Integer> resultPos) {

		SortedSet<Integer> sortedPositions = new TreeSet<Integer>(
				resultPos.values());
		// smallest holds the start index of the first query term, and biggest
		// holds the end index of last query term
		int smallest = sortedPositions.first();
		int biggest = SnippetUtil.getWhitespaceAfterWord(doc, sortedPositions.last());

		// find number of words between smallest and biggest
		String extendedSnippet = SnippetUtil.getFullSnippet(doc, smallest, biggest);
		String[] snippetTokens = SnippetUtil.tokenize(extendedSnippet);
		String snippet = null;

		// if the snippet length exceed the defined SNIPPET_LENGTH, then
		// eliminate the search tokens that are farthest from the beginning of
		// the document.
		while (snippetTokens.length > SNIPPET_LENGTH) {
			sortedPositions.remove(sortedPositions.last());
			biggest = SnippetUtil.getWhitespaceAfterWord(doc, sortedPositions.last());
			snippetTokens = SnippetUtil.tokenize(SnippetUtil.getFullSnippet(doc, smallest, biggest));
		}

		// if the snippet length is exactly equal to the defined SNIPPET_LENGTH,
		// then return the snippet.
		if (snippetTokens.length == SNIPPET_LENGTH) {
			snippet = extendedSnippet;
			return snippet.trim();
		}

		//if the snippet length is less than defined SNIPPET_LENGTH, then proceed to extract the snippet with equal distribution of e.
		if (snippetTokens.length < SNIPPET_LENGTH) {
			// calculate words to append on either side of the words
			int wordsToAppend = (SNIPPET_LENGTH - snippetTokens.length) / 2;
			System.out.println("words to append: " + wordsToAppend);

			int leftWordCount = wordsToAppend;
			int rightWordCount = SNIPPET_LENGTH - snippetTokens.length
					- wordsToAppend;
			int i = smallest;
			int j = biggest;
			int possibleLeftWordCount = SnippetUtil.leftWordsToAppend(doc, i,
					leftWordCount);
			int possibleRightWordCount = SnippetUtil.rightWordsToAppend(doc, j,
					rightWordCount);
			if (possibleLeftWordCount != leftWordCount
					|| possibleRightWordCount != rightWordCount) {

				if (possibleLeftWordCount < leftWordCount) {
					rightWordCount += leftWordCount - possibleLeftWordCount;
					leftWordCount = possibleLeftWordCount;
				} else if (possibleRightWordCount < rightWordCount) {
					leftWordCount += rightWordCount - possibleRightWordCount;
					rightWordCount = possibleRightWordCount;

				}
			}
			SnippetType snippetType = SnippetUtil.getSnippetType(leftWordCount,
					rightWordCount);

			switch (snippetType) {
			case SNIPPET_FROM_START_OF_DOC:
				snippet = SnippetUtil.getRightWords(doc, rightWordCount, j);

				break;
			case SNIPPET_WITH_END_OF_DOC:
				i = smallest - 2;
				snippet = SnippetUtil.getLeftWords(doc, leftWordCount, i);
				break;
			case SNIPPET_FROM_MIDDLE:
				i = smallest - 2;
				snippet = SnippetUtil.getLeftAndRightWords(doc, leftWordCount,
						rightWordCount, i, j);
				break;
			default:
				break;
			}
		}

		return snippet.trim();
	}

	

	
	

	/**
	 * Add the highlights around the query tokens in the snippet.
	 * 
	 * @param queryTokens
	 * @param snippet
	 * @return
	 */
	public String addHighlights(String[] queryTokens, String snippet) {
		for (int i = 0; i < queryTokens.length; i++) {

			Pattern pattern = Pattern.compile(queryTokens[i],
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher matcher = pattern.matcher(snippet.trim());
			if (matcher.find()) {
				snippet = matcher.replaceAll("<HIGHLIGHT>" + matcher.group()
						+ "</HIGHLIGHT>");
			}

		}
		return snippet;
	}

	/**
	 * Populate the map with only the query tokens that are found in the
	 * document.
	 * 
	 * @param doc
	 * @param queryTokens
	 * @return
	 */
	public Map<String, ArrayList<Integer>> populateMap(String doc,
			String[] queryTokens) {

		Map<String, ArrayList<Integer>> map = new LinkedHashMap<String, ArrayList<Integer>>();
		for (int i = 0; i < queryTokens.length; i++) {

			Pattern pattern = Pattern.compile(queryTokens[i],
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher matcher = pattern.matcher(doc);

			while (matcher.find()) {
				if (map.containsKey(queryTokens[i])) {

					map.get(queryTokens[i]).add(matcher.start());
				} else {
					ArrayList<Integer> tempList = new ArrayList<Integer>();
					tempList.add(matcher.start());
					map.put(queryTokens[i], tempList);
				}
			}

		}
		return map;
	}

}
