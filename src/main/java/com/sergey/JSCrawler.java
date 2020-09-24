package com.sergey;

import java.util.List;

/**
 * @author Sergey Zverlov
 */

public class JSCrawler {
	public static void main(String[] args) throws Exception {
			
			List<String> arguments = SpiderPigUtils.removeInvalidArguments(args);
			if(arguments.size()<=0) {
				throw new Exception("This crawler needs at least one search word as an argument that is not a white space. Correct usage: JSCrawler argument1 argument2 ...");
			}
			
			SpiderPig oink = new SpiderPig(arguments);
			oink.crawl();
			
		}
}
