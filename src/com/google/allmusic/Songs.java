package com.google.allmusic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Songs implements SearchTypes {// collection of song instances, used to form JSON string for song search result
	public List<Song> songs;
	public Songs() {
		songs = new ArrayList<Song>();
	}
	public Songs(List<Song> songs) {
		this.songs = songs;
	}
}

class Song implements SearchType {// song search type
	public String title, performer, composer, details;
	public Song() {
	}
	@Override
	public void parseData(Document document, List types, String BASE_URL) {// create result table for songs information
		// TODO Auto-generated method stub
		types = (List<Song>) types;
		for (Element child : document.getElementsByTag("li")) {
			Song curr = new Song();
			try {
				curr.title = child.getElementsByClass("title").get(0).getElementsByTag("a").text();
			} catch (Exception e) {}
			try {
				curr.details = child.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
				if (curr.details.indexOf("http://") == -1) {
					curr.details = BASE_URL + curr.details; 
				}
			} catch (Exception e) {}
			try {
				curr.performer = child.getElementsByClass("performers").get(0).getElementsByTag("a").text();
			} catch (Exception e) {}
			try {
				curr.composer = child.getElementsByClass("composers").get(0).getElementsByTag("a").text();
			} catch (Exception e) {}
			types.add(curr);
		}
	}
	@Override
	public String makeURL(String BASE_URL, Query query) {// generate specific url string for song search
		try {
			return BASE_URL + "search/songs/" + URLEncoder.encode(query.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
