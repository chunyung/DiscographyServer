package com.google.allmusic;

import java.util.List;

import org.jsoup.nodes.Document;


public interface SearchTypes {// interface for search types
}

interface SearchType {
	public void parseData(Document document, List types, String BASE_URL);
	public String makeURL(String BASE_URL, Query query);
}
