package com.google.allmusic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//Spring framework controller setting
@Controller
public class MusicController {
	final String BASE_URL = "http://www.allmusic.com/";
	@RequestMapping(value = "/search")
	public ModelAndView searchPage() {// use ModelAndView to create entrance page for searching
		ModelAndView model = new ModelAndView("search");
		model.addObject("types", Arrays.asList("Artists", "Albums", "Songs"));
		return model;
	}
	
	@RequestMapping(value = "/artists", method = RequestMethod.POST)
	public @ResponseBody Artists getArtist(@RequestBody Query query) {// entrance for artist type search
		String url = new Artist().makeURL(BASE_URL, query);// create specific format for request url for different search type
		if (url == null) {
			return null;
		}
		//return JSON format response of artists (collections of artist)
		return new Artists(parseData(extractData(url), Artist.class, BASE_URL));
	}
	
	@RequestMapping(value = "/songs", method = RequestMethod.POST)
	public @ResponseBody Songs getSong(@RequestBody Query query) {// entrance for song type search
		String url = new Song().makeURL(BASE_URL, query);// create specific format for request url for different search type
		if (url == null) {
			return null;
		}
		//return JSON format response of songs (collections of song)
		return new Songs(parseData(extractData(url), Song.class, BASE_URL));
	}

	@RequestMapping(value = "/albums", method = RequestMethod.POST)
	public @ResponseBody Albums getAlbum(@RequestBody Query query) {// entrance for album type search
		String url = new Album().makeURL(BASE_URL, query);// create specific format for request url for different search type
		if (url == null) {
			return null;
		}
		//return JSON format response of albums (collections of album)
		return new Albums(parseData(extractData(url), Album.class, BASE_URL));
	}
	
	private String extractData(String url) {//extract useful section from the web page using regular expression
		String content = null;
		content = MusicConnection.getContent(url);
		String regExp = "(<ul class=\"search-results\">.*</ul>)";
		Matcher matcher = Pattern.compile(regExp, Pattern.DOTALL).matcher(content);
		if (matcher.find()) {
			content = matcher.group(1);
		}
		return content;
	}
	
	//invoke specific method for different types of search
	private List parseData (String content, Class classType, String BASE_URL) {
		List types = new ArrayList();
		Document document = Jsoup.parse(content);//parse the webpage using Jsoup
		Class[] parms = new Class[] {Document.class, List.class, String.class};
		try {
			classType.getMethod("parseData", parms).invoke(classType.newInstance(), document, types, BASE_URL);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			e.printStackTrace();
		}
		return types;
	}
}
