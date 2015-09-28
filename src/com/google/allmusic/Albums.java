package com.google.allmusic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Albums implements SearchTypes { // collection of album instances, used to form JSON string for album search result
	public List<Album> albums;
	public Albums() {
		this.albums = new ArrayList<Album>();
	}
	public Albums(List<Album> albums) {
		this.albums = albums;
	}
}

class Album implements SearchType {// album search type
	public String cover, title, artist, genre, year, details;
	public Album() {
	}
	@Override
	public void parseData(Document document, List types, String BASE_URL) {// create result table for albums information
		// TODO Auto-generated method stub
		types = (List<Album>) types;
		for (Element child : document.getElementsByTag("li")) {
			Album curr = new Album();
			try {
				curr.cover = child.getElementsByClass("cover").get(0).getElementsByTag("a").get(0).getElementsByTag("noscript").get(0).getElementsByTag("img").get(0).attr("src");
				if (curr.cover.indexOf("http://") == -1) {
					curr.cover = BASE_URL + curr.cover;
				}
			} catch (Exception e) {}
			try {
				curr.details = child.getElementsByClass("cover").get(0).getElementsByTag("a").get(0).attr("href");
				if (curr.details.indexOf("http://") == -1) {
					curr.details = BASE_URL + curr.details; 
				}
			} catch (Exception e) {}
			try {
				curr.artist = child.getElementsByClass("artist").get(0).getElementsByTag("a").get(0).text();
			} catch (Exception e) {}
			try {
				curr.title = child.getElementsByClass("title").get(0).getElementsByTag("a").get(0).text();
			} catch (Exception e) {}
			try {
				curr.year = child.getElementsByClass("year").get(0).text();
			} catch (Exception e) {}
			try {
				curr.genre = child.getElementsByClass("genres").get(0).text();
			} catch (Exception e) {}
			types.add(curr);
		}
	}
	@Override
	public String makeURL(String BASE_URL, Query query) {// generate specific url string for album search
		try {
			return BASE_URL + "search/albums/" + URLEncoder.encode(query.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
