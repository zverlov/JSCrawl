package com.sergey;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SpiderPigUtilsTest {

	@Test
	public void testCreateSearchString() {
		String searchString = "https://www.google.com/search?q=noobs&num=20";
		List<String> searchWords = new ArrayList<String>();
		searchWords.add("noobs");
		assertEquals(searchString, SpiderPigUtils.createSearchString(searchWords, 20));
	}
	
	@Test
	public void testGetDomainPositive() {
		String input = "/url?q=https://www.website.org/&sa=U&ved=2ahUKEwjprpnJ_KvrAhUTv54KHX1oAy4QFjAAegQIARAB&usg=AOvVaw3n5JQ32GtcKnYM_NnL9ox-";
		String expectedOutput = "https://www.website.org";
		assertEquals(expectedOutput, SpiderPigUtils.getNormalizedUrl(input));
	}
	
	@Test
	public void testGetDomainNegative() {
		String input = "wrongInputUrlwww";
		String expectedOutput = "";
		assertEquals(expectedOutput, SpiderPigUtils.getNormalizedUrl(input));
	}
	
	@Test
	public void testRemoveInvalidArguments() {
		String[] inputArray = {"argument1", "argument2", "argument3", "", "   "};
		int numberOfValidArguments = 3;
		assertEquals(numberOfValidArguments, SpiderPigUtils.removeInvalidArguments(inputArray).size());
	}

}
