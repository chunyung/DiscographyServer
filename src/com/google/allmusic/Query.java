package com.google.allmusic;

import java.util.ArrayList;
import java.util.List;

public class Query {// used to form query object and Jackson JSON api to form JSON request and response between client and server
	public List<String> keywords;
	public Query() {
		this.keywords = new ArrayList<String>();
	}
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (String elem : this.keywords) {
			result.append(elem).append('+');
		}
		return result.toString().substring(0, result.length() - 1);
	}
}
