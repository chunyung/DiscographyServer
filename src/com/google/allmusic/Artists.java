package com.google.allmusic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Artists implements SearchTypes { // collection of artist instances, used to form JSON string for artist search result
	public List<Artist> artists;
	public Artists() {
		this.artists = new ArrayList<Artist>();
	}
	public Artists(List<Artist> artists) {
		this.artists = artists;
	}
}
class Artist implements SearchType {// artist search type
	public String cover, artist, genre, year, details;
	public Artist() {	
	}
	@Override
	public void parseData(Document document, List types, String BASE_URL) {// create result table for artists information
		// TODO Auto-generated method stub
		types = (List<Artist>) types;
		for (Element child : document.getElementsByTag("li")) {
			Artist curr = new Artist();
			try {
				curr.cover = child.getElementsByTag("img").get(0).attr("src");
				if (curr.cover.indexOf("http://") == -1) {
					curr.cover = BASE_URL + curr.cover;
				}
			} catch (Exception e) {}
			try {
				curr.artist = child.getElementsByClass("name").get(0).child(0).text();
			} catch (Exception e) {}
			try {
				curr.details = child.getElementsByClass("name").get(0).child(0).attr("href");
				if (curr.details.indexOf("http://") == -1) {
					curr.details = BASE_URL + curr.details; 
				}
			} catch (Exception e) {}
			try {
				curr.genre = child.getElementsByClass("genres").get(0).ownText();
			} catch (Exception e) {}
			try {
				curr.year = child.getElementsByClass("decades").get(0).text();
			} catch (Exception e) {}
			types.add(curr);
		}
	}
	@Override
	public String makeURL(String BASE_URL, Query query) {// generate specific url string for artist search
		// TODO Auto-generated method stub
		try {
			return BASE_URL + "search/artists/" + URLEncoder.encode(query.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
