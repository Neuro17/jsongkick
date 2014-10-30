package jsongkick;

import http.SongkickConnector;

import java.awt.Event;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import entity.SongkickArtist;
import search.ArtistSearch;
import search.EventSearch;
import search.LocationSearch;

public class App {
	
	private static final Logger log = LogManager.getLogger(App.class);

	
	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		ArtistSearch artistSearch = new ArtistSearch();
		LocationSearch locationSearch = new LocationSearch();
		EventSearch eventSearch = new EventSearch();
		
		ArrayList <SongkickArtist> artists = new ArrayList<SongkickArtist>();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
//		sa.openConnection();
		
//		artists = sa.list("pearl jam");
//		log.info(artistSearch.firstArtist("nirvana"));
//		
//		for(SongkickArtist artist : artists){
//			log.info(artist.toString());
//		}
////		
////		TODO
//		
//		
//		ArrayList<String> arr = new ArrayList<String>();
//		arr.add("312553");
//		arr.add("7903054");
//		arr.add("7947163");
////		http://api.songkick.com/api/3.0/events.json?apikey={your_api_key}
//		for(String id : arr){
//			URL url = new URL("http://api.songkick.com/api/3.0/artists/"+id+"/gigography.json?apikey=iF1N0jYrhI5wtG3n");
//			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//			
//			StringBuffer result = new StringBuffer();
//			
//			String line = "";
//			
//			while ((line = in.readLine()) != null) {
//			    result.append(line);
//			}
//			
//			log.debug(gson.toJson(result.toString()));
//		}
		
//		http://api.songkick.com/api/3.0/events.json?apikey={your_api_key}&artist_name=pixies&min_date=2009-10-01&max_date=2009-10-30
//		URL url = new URL("http://api.songkick.com/api/3.0/search/locations.json?query=milan&apikey=iF1N0jYrhI5wtG3n");
//		JsonObject location = artistSearch.parseResponseAsJson(url.openStream());
////		log.debug(gson.toJson(location));
//		int id = location.getAsJsonObject().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0).getAsJsonObject().getAsJsonObject("metroArea").get("id").getAsInt();
//		url = new URL("http://api.songkick.com/api/3.0/events.json?apikey=iF1N0jYrhI5wtG3n&location=sk:"+id);
//		JsonObject events = artistSearch.parseResponseAsJson(url.openStream());
//		log.debug(gson.toJson(events));		
		
		String id = locationSearch.findId("modena");

		JsonObject events = eventSearch.eventsList(id);
				
		log.debug(gson.toJson(events.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("event").get(0)));
	}
}
