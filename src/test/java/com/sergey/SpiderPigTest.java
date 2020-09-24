package com.sergey;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SpiderPigTest {

	@Test
	public void testGetGoogleResultsPositive() {
		List<String> searchWords = new ArrayList<String>();
		searchWords.add("games");
		searchWords.add("computer");
		SpiderPig oink = new SpiderPig(searchWords);
		assertTrue(oink.getGoogleResults(searchWords, 10 ).size()>0);
		
	}
	
	@Test
	public void testGetGoogleResultsNegative() {
		List<String> searchWords = new ArrayList<String>();
		searchWords.add("adfdasfjökladsf");
		searchWords.add("comasdfasdfasfsdputer");
		SpiderPig oink = new SpiderPig(searchWords);
		assertFalse(oink.getGoogleResults(searchWords, 10 ).size()>0);
		
	}
	
	@Test
	public void testGetWebDocumentPositive() {
		List<String> searchWords = new ArrayList<String>();
		searchWords.add("searchWord");
		SpiderPig oink = new SpiderPig(searchWords);
		assertTrue(oink.getWebDocument("https://www.google.de") != null);
	}
	
	@Test
	public void testGetWebDocumentNegative() {
		List<String> searchWords = new ArrayList<String>();
		searchWords.add("searchWord");
		SpiderPig oink = new SpiderPig(searchWords);
		assertTrue(oink.getWebDocument("https://not-a-valid-website.ruorg") == null);
	}

}
