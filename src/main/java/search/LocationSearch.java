package search;


import http.SongkickConnector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import config.SongkickConfig;
import entity.FullLocation;
import entity.SimpleLocation;

public class LocationSearch extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(LocationSearch.class);
	
	public LocationSearch() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		uriBld = new URIBuilder();
	}
	
	public void search(String locationName) throws URISyntaxException{
		buildURI();
		
		uri = query(locationName);
		
		executeRequest(uri);
	}
	
	public SimpleLocation firstLocation(String locationName) throws URISyntaxException{
		//TODO (CONTROLLARE) Cambiare il valore di ritorno in Location
		SimpleLocation l = null;
		JsonElement firstLocation = null; 
		
		log.trace("Retrieving location");
		
		search(locationName);
/*		VECCHIO DA CANCELLARE
		id = checkResponse() ? 
				getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0).getAsJsonObject().getAsJsonObject("metroArea").get("id").getAsString() 
				: null;
*/		
		if(!checkResponse()){
			return l;
		}
		
		firstLocation = getJsonResponse().getAsJsonObject("resultPage").getAsJsonObject("results").getAsJsonArray("location").get(0);

//	da: http://www.songkick.com/developer/upcoming-events-for-artist		
		l = new SimpleLocation(
				firstLocation.getAsJsonObject().getAsJsonObject("city").getAsJsonObject("lat").getAsInt(), 
				firstLocation.getAsJsonObject().getAsJsonObject("city").getAsJsonObject("lng").getAsInt(), 
				firstLocation.getAsJsonObject().getAsJsonObject("city").getAsJsonObject("displayName").getAsString());

		//		id = getJsonResponse().getAsJsonObject().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0).getAsJsonObject().getAsJsonObject("metroArea").get("id").getAsString();
		
		log.trace("Successfully retrieved location");
		return l;
	}
	
	public ArrayList<FullLocation> list(String locationName) throws URISyntaxException{
		//TODO _ implementare getAsCity() e getAsMetroArea() 
		log.trace("Retrieving location list");
		JsonElement locationsAsJson = null;
		ArrayList<FullLocation> locations = new ArrayList<FullLocation>();
		
		search(locationName);
		
		if(!checkResponse()){
			return locations;
		}
//da : http://www.songkick.com/developer/upcoming-events-for-artist
		locationsAsJson = getJsonResponse().getAsJsonObject("resultPage").getAsJsonObject("results").getAsJsonArray("location");

		for(JsonElement location : locationsAsJson.getAsJsonArray() ){
//			log.debug(gson.toJson(location.getAsJsonObject().getAsJsonObject("city").getAsCity()));
//			log.debug(gson.toJson(location.getAsJsonObject().getAsJsonObject("metroArea").getAsMetroArea()));
//			locations.add(new FullLocation(	location.getAsJsonObject().getAsJsonObject("city").getAsAsCity(), 
//											location.getAsJsonObject().getAsJsonObject("metroArea").getAsAsCity()));
		}
		
		log.trace("Succesfully retrieved locations");
		return locations;
	}
	
	@Override
	public URI query(String locationName) throws URISyntaxException{
		return uriBld	.setPath(SongkickConfig.getLocationPath())
						.setParameter("query", locationName)
						.setParameter("apikey", SongkickConfig.getApiKey())
						.build();
	}

}
