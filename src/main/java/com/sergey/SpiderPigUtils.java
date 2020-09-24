package com.sergey;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sergey Zverlov
 */

public class SpiderPigUtils {
	/* Returns a normalized URL (http(s)://www.domain-name.xx) as @{String}. */
	public static String getNormalizedUrl(String url){
		Matcher matcher;
		String domainName = "";
		String DOMAIN_NAME_PATTERN = "(http://|https://)([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,8}";
		matcher = Pattern.compile(DOMAIN_NAME_PATTERN).matcher(url);
		if (matcher.find()) {
			domainName = matcher.group(0).toLowerCase().trim();
		}
		return domainName;

	  }
	
	/* Creates a google search string for a @{List} of search words for @{Integer} number of results. */
	public static String createSearchString(List<String> searchWords, int nr_of_search_results) {
		StringBuffer res = new StringBuffer("https://www.google.com/search?q=");
		for(int i=0; i<searchWords.size()-1; i++) {
			res.append(searchWords.get(i)+"+");
		}
		res.append(searchWords.get(searchWords.size()-1));
		res.append("&num=" + String.valueOf(nr_of_search_results));
		
		return res.toString();
	}
	
	/* Removes spaces and tabs, since those are not valid search words. */
	public static List<String> removeInvalidArguments(String[] args){
		List<String> validArguments = new ArrayList<String>();

		for(String argument : args) {
			argument = argument.replaceAll("\\s+", "");
			if(argument.length() > 0) {
				validArguments.add(argument);
			}
		}
		return validArguments;
	}
}
