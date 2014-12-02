package search;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import config.SongkickConfig;
import entity.Artist;
import entity.Concert;
import entity.FullLocation;
import entity.MetroArea;
import entity.Venue;
import http.SongkickConnector;

public class EventSearch extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(EventSearch.class);
	
	private JsonObject events;
//	private Location location;
	private MetroArea metroarea;
	private Venue venue;
	private Concert concert; 
	private ArrayList<Concert> concerts;
	private ArrayList<Artist> artists;
	private Gson gson;

	public EventSearch() {
		uriBld = new URIBuilder();
		gson = new GsonBuilder().setPrettyPrinting().create();
		events = new JsonObject();
		concerts = new ArrayList<Concert>();
	}
	
	public JsonObject getEvents() {
		return events;
	}

	public void setEvents(JsonObject events) {
		this.events = events;
	}

	@Override
	public URI query(String locationId) throws URISyntaxException {
		log.trace("setting parameters to query");
		
		return uriBld	.setPath(SongkickConfig.getEventPath())
						.setCustomQuery("location=sk:"+locationId+
										"&apikey="+SongkickConfig.getApiKey())
						.build();	
	}
	
	public URI query(String locationId, int pageNumber){
		log.trace("setting parameters to query");
		
		try {
			return uriBld.setPath(SongkickConfig.getEventPath()).setCustomQuery("location=sk:"+locationId+"&apikey="+SongkickConfig.getApiKey()+"&page="+pageNumber).build();
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}
	
	public ArrayList<Concert> eventsListByLocationId(String locationId) throws URISyntaxException{
		//TODO (CONTROLLARE)- aggiungere l'array di performance; ho modificato extractConcert
		
		log.trace("entering eventList by location id");
		
		buildURI();
		
		uri = query(locationId, 1);
		
		log.debug("uri built: " + uri);
		
		executeRequest(uri);
		 
		events = getJsonResponse();
		if(events.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() > events.getAsJsonObject("resultsPage").get("perPage").getAsInt()){
			//capire se conviene gestire qui il fatto che ci possono essere più pagine nei risultati. è un problema comune a tutte le richieste
		}
//		log.debug(gson.toJson(events.toString()));: 
		JsonArray listTmp = events.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("event");
		
		for(JsonElement item : listTmp){
			concerts.add(Extractor.extractConcert(item));	
		}
	
//		log.debug(listTmp.get(0).toString());
//		concerts = events.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("event");
		
		log.trace("exiting eventsListByLocationId");
		return concerts;
	}
	
	public URI queryByArtistId(String artistId) throws URISyntaxException{		
		//TODO 	(CONTROLLARE) stampa correttamente 
		// 		/artists/artists/123456789/calendar.json?apikey=iF1N0jYrhI5wtG3n
		try {
			return uriBld	.setPath(SongkickConfig.getArtistPathForEvent())
							.setPath(SongkickConfig.getArtistPathForEvent() + 
									"/" + artistId +
									SongkickConfig.getArtistPathForEventCalendar())
							.setCustomQuery("apikey=" + SongkickConfig.getApiKey())
							.build();
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	public ArrayList<Concert> eventsListByArtistId(String artistId) throws URISyntaxException{
		//TODO (CONTROLLARE)
		log.trace("entering eventLyst by artist id");
		
		buildURI();
		
		uri = queryByArtistId(artistId);
		
		log.debug("uri built: " + uri);
		
		executeRequest(uri);

		events = getJsonResponse();
		if(events.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() > events.getAsJsonObject("resultsPage").get("perPage").getAsInt()){
			//capire se conviene gestire qui il fatto che ci possono essere più pagine nei risultati. è un problema comune a tutte le richieste
		}
		
		JsonArray listTmp = events.getAsJsonObject("resultPage").getAsJsonObject("result").getAsJsonArray("event");
		
		for(JsonElement item : listTmp){
			concerts.add(Extractor.extractConcert(item));
		}
		
		log.trace("exiting eventsListByArtistId");

		return concerts;
	}
	
	public JsonObject toJson(){
		//TODO - trasformare l'oggetto Concert in JsonObject.
		return null;
	}

}