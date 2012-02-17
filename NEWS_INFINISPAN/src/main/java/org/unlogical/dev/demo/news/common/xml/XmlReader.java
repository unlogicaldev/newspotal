package org.unlogical.dev.demo.news.common.xml;


public interface XmlReader {
	
	/**
	 * rss reader
	 * @param rssUrl
	 * @param category
	 */
	void rssRead(String rssUrl, String category);
}
