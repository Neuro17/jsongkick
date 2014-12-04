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
		

		ArtistSearch artistSearch = new ArtistSearch();
		LocationSearch locationSearch = new LocationSearch();
		EventSearch eventSearch = new EventSearch();
		ArrayList <Artist> artists = new ArrayList<Artist>();
		Artist art = artistSearch.firstArtist("Metallica");
		log.debug(art.toString());
	
		
	
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
//		test firstLocation
		log.debug("test firstLocation -------------------------------------------");
		FullLocation fl = locationSearch.firstLocation("rome");
		log.debug("location found : " + fl.getMetroarea() + " - " + fl.getCity());
		
//		Test eventsListByLocationId
		log.debug("Test eventsListByLocationId -------------------------------------------");
		FullLocation fullLocation = locationSearch.firstLocation("new york");
		ArrayList<Concert> events = eventSearch.eventsListByLocationId(fullLocation.getMetroarea().getId());
		
		for(Concert event : events){
			if(event.getPopularity() > 0.3)
				log.debug(event.toString());
		}
		
//		Test location list
		log.debug("Test location list -------------------------------------------");
		ArrayList<FullLocation> fullLocations = locationSearch.list("berlin");
		
		for(FullLocation fullLoc : fullLocations)
			log.debug("location found : " + fullLoc.getMetroarea() + " - " + fullLoc.getCity());	
		
		//test uri artist search
		log.debug("test uri artist search -------------------------------------------");
		art = artistSearch.firstArtist("2cellos");
		
		log.debug(eventSearch.queryByArtistId(art.getId()));
		
		//Test eventsListByArtistId
		log.debug("Test eventsListByArtistId -------------------------------------------");
		ArrayList<Concert> concerts = eventSearch.eventsListByArtist(art);
		for(Concert concert : concerts){
			log.debug(concert.toString());
		}

	}	
	public static void main(String[] args){
		try {
			App.run();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
}
