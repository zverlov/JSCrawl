package com.sergey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Sergey Zverlov
 */

public class SpiderPig {
	/* @{List} of search words. */
	private List<String> searchWords;
	
	/* A @{HashMap} that holds the library names and the number of their occurrences. */
	private HashMap<String, Integer> libCount;
	
	/* The (fake) name of the user agent as @{String}.*/
	private final String USER_AGENT = "Mozilla";
	
	/* Timeout for each HTTP request as @{Integer} in ms. */
	private final int TIME_OUT = 50000;
	
	/* Max number of search results that will be crawled as @{Integer}. */
	private final int NR_OF_SEARCH_RESULTS = 20;
	
	/* Default setting (top 5) can be changed to top x via this @{Integer} variable. */
	private final int TOP_X = 5;


	
	/* Constructor that requires a @{List} of search words. */
	public SpiderPig(List<String> searchWords) {
		this.searchWords = searchWords;
		this.libCount = new HashMap<String, Integer>();
	}
	
	/* Main crawling function, that triggers the google search and looks for 
	 * JS libraries in each of the results. */
	public void crawl() throws Exception {
		List<String> searchResults = getGoogleResults(this.searchWords, this.NR_OF_SEARCH_RESULTS);
		if(searchResults.size() <=0) {
			throw new Exception("No google results were found for the following search words: " + this.searchWords.toString());
		}
		
		for(String res : searchResults) {
			crawlEachResult(res);
		}
		
		PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<Map.Entry<String, Integer>>(new JSLibComparator());
		for(Map.Entry<String, Integer> entry : this.libCount.entrySet()) {
			pq.offer(entry);

		}
		System.out.println("\n-----------Top " + TOP_X + " JS Files-------------");
		for(int i=1; i<TOP_X+1; i++) {
			Map.Entry<String, Integer> entry =pq.poll();
			if (entry == null)
				break;
			System.out.println("#" + i + " Lib Name: " + entry.getKey() + " Occurences: " + entry.getValue());
		}
		
		System.out.println("-----------------------------------");
		
	}

	/* Returns a web document from an URL that is passed as a @{String}. */
	protected Document getWebDocument(String url) {
		Document resDoc = null;
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).timeout(TIME_OUT);
			resDoc = connection.get();
			// checking if connection is fine
			if(connection.response().statusCode() == 200) {
					System.out.println("\n Visiting: " + url);
			}
			//checking if we receive a HTML document
			if(!connection.response().contentType().contains("text/html")) {
				System.out.println("Not a HTML document.");
				return resDoc;
			}
			return resDoc;
		} catch (IOException e) {
			System.out.println("Document with the following url could not be fetched: " + url + " ");
			return resDoc;
		}
	}
	
	/* Returns a @{List} of google results for the @{List} of given search words. 
	 * The amount of results is given as an @{Integer}. */
	protected List<String> getGoogleResults(List<String> searchWords, int nrOfResults){
		List<String> res = new ArrayList<String>();
		String url = SpiderPigUtils.createSearchString(searchWords, nrOfResults);
		HashSet<String> domainMap = new HashSet<String>();
		Document googleResults = getWebDocument(url);
		Elements linksOnPage = googleResults.select("a[href]");
		for(Element link : linksOnPage) {

			String resultCandidate = link.attr("href");
			if(resultCandidate.startsWith("/url?q=")){
		        String domainName = SpiderPigUtils.getNormalizedUrl(resultCandidate);
		        // Assumption: I exclude results, that contain google in the domain, 
		        // since googling for google is redundant.
		        // Furthermore, I ignore redundant and empty domains.
				if(!domainMap.contains(domainName) && !domainName.contains("google") && !domainName.equals("")) {
					res.add(domainName);
					domainMap.add(domainName);
				}
				
			}

		}
		
		return res;
	}
	
	/* This method searches for JS libraries in a document, specified by an URL as a @{String}.*/
	public void crawlEachResult(String url) {
		Document googleResult = getWebDocument(url);
		if(googleResult == null)
			return;
		Elements jsLibs = googleResult.select("script[src$=.js]");
		for(Element lib : jsLibs) {
			String[] testList = lib.absUrl("src").split("/");
			String jsLib = testList[testList.length-1];
			this.libCount.put(jsLib, this.libCount.getOrDefault(jsLib, 0)+1);
		}      
	}
	
	
	/* Custom @{Comparator} for the @{PriorityQueue}. */
	class JSLibComparator implements Comparator<Map.Entry<String, Integer>>{

		public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
			return Integer.compare(o2.getValue(), o1.getValue());
		}

		
	}
	
	
}
