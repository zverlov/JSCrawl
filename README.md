Installation with Maven: mvn package

Usage: java -jar target/JSCrawler-0.0.1-SNAPSHOT.jar searchword1 serachword2 etc

Functionality:

Crawler goes to google and searches for (a concatenation) of the search words (i.e. search string).
For each of the google results crawler looks for java script libraries (i.e.: .js files) and stores the nubmer of their occurences.
Crawler returns a top X (currently top 5) of most frequently used java script libraries.

Assumptions and thoughts:

My assumption is that any .js file that is referenced on a website is a Java script library.

Same libraries with slighty different names: Currently I assume that .js files with different names (even if it is one character) are different libraries. That can be addressed by either normalizing the library names (e.g. getting rid of version numbers) or having a list of library names to match the found files against.
