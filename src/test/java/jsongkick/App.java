package jsongkick;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import search.ArtistSearch;
import search.EventSearch;
import search.LocationSearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Artist;
import entity.Concert;
import entity.FullLocation;


public class App {
	
	private static final Logger log = LogManager.getLogger(App.class);

	public static void run() throws URISyntaxException{
//		funzia
//		LocationSearch ls = new LocationSearch();
//		FullLocation fl = ls.firstLocation("rome");
//		log.trace("location found : " + fl.getMetroarea() + " - " + fl.getCity());		

//		ArtistSearch artistSearch = new ArtistSearch();
//		LocationSearch locationSearch = new LocationSearch();
//		EventSearch eventSearch = new EventSearch();

		
//		Artist art = artistSearch.firstArtist("Metallica");
//		log.debug(art.toString());
//		
//		ArrayList <Artist> artists = new ArrayList<Artist>();
//		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();

//		FullLocation fullLocation = locationSearch.firstLocation("new york");
//		ArrayList<FullLocation> fullLocations = locationSearch.list("london");
//		for(FullLocation fl : fullLocations)
//			log.trace("location found : " + fl.getMetroarea() + " - " + fl.getCity());
		
//		ArrayList<Concert> events = eventSearch.eventsListByLocationId(fullLocation.getMetroarea().getId());
		
//		for(Concert event : events){
//			if(event.getPopularity() > 0.3)
//				log.debug(event.toString());
//		}
		
//		log.debug(events.get(0));
	}
	
	public static void main(String[] args){
		try {
			App.run();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	
//	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
//		ArtistSearch artistSearch = new ArtistSearch();
//		LocationSearch locationSearch = new LocationSearch();
//		EventSearch eventSearch = new EventSearch();
//		
//		ArrayList <Artist> artists = new ArrayList<Artist>();
//		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		
////		sa.openConnection();
//		
//		log.debug(new DateTime());
//		
//		String string = "2014-11-01T21:00:00+01002";
//		string = string.replace("T", " ");
////		string = string.replaceAll("?+", "");
//		
////		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
////		DateTime dt = formatter.parseDateTime(string);
////		log.debug(dt);
//		
////		artists = sa.list("pearl jam");
////		log.info(artistSearch.firstArtist("nirvana"));
////		
////		for(SongkickArtist artist : artists){
////			log.info(artist.toString());
////		}
//////	
////		
////		
////		ArrayList<String> arr = new ArrayList<String>();
////		arr.add("312553");
////		arr.add("7903054");
////		arr.add("7947163");
//////		http://api.songkick.com/api/3.0/events.json?apikey={your_api_key}
////		for(String id : arr){
////			URL url = new URL("http://api.songkick.com/api/3.0/artists/"+id+"/gigography.json?apikey=iF1N0jYrhI5wtG3n");
////			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
////			
////			StringBuffer result = new StringBuffer();
////			
////			String line = "";
////			
////			while ((line = in.readLine()) != null) {
////			    result.append(line);
////			}
////			
////			log.debug(gson.toJson(result.toString()));jsonElement
////		}
//		
////		http://api.songkick.com/api/3.0/events.json?apikey={your_api_key}&artist_name=pixies&min_date=2009-10-01&max_date=2009-10-30
////		URL url = new URL("http://api.songkick.com/api/3.0/search/locations.json?query=milan&apikey=iF1N0jYrhI5wtG3n");
////		JsonObject location = artistSearch.parseResponseAsJson(url.openStream());
//////		log.debug(gson.toJson(location));
////		int id = location.getAsJsonObject().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0).getAsJsonObject().getAsJsonObject("metroArea").get("id").getAsInt();
////		url = new URL("http://api.songkick.com/api/3.0/events.json?apikey=iF1N0jYrhI5wtG3n&location=sk:"+id);
////		JsonObject events = artistSearch.parseResponseAsJson(url.openStream());
////		log.debug(gson.toJson(events));		
//		
//		String id = locationSearch.findId("milano");
//		
//		ArrayList<Concert> events = eventSearch.eventsListById(id);
//		
//		for(Concert event : events.subList(0, 10)){
//			log.debug(event.toString());
//		}
//				
////		log.debug(gson.toJson(events.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("event").get(0)));
//	}
	
}
