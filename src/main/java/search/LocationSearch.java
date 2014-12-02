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
import entity.City;
import entity.FullLocation;
import entity.MetroArea;
import entity.SimpleLocation;
import search.Extractor;

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
	
	public FullLocation firstLocation(String locationName) throws URISyntaxException{
		//TODO doing ... 
		FullLocation l;
		JsonElement firstLocationAsJson = null;
		JsonElement metroAreaAsJson = null;
		JsonElement cityAsJson = null;
		
		log.trace("Retrieving first location");
		
		search(locationName);
		
		if(!checkResponse()){
			l = null;
		}
		
		firstLocationAsJson = getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0);
		metroAreaAsJson = firstLocationAsJson.getAsJsonObject().getAsJsonObject("metroArea");
		cityAsJson = firstLocationAsJson.getAsJsonObject().getAsJsonObject("city");

		//	da: http://www.songkick.com/developer/location-search		
		
//		cityAsJson = firstLocationAsJson.getAsJsonObject().getAsJsonObject("city");
//		metroAreaAsJson = firstLocationAsJson.getAsJsonObject().getAsJsonObject("metroArea");

		l = new FullLocation(	Extractor.extractMetroArea(metroAreaAsJson),
								Extractor.extractCity(cityAsJson));

		log.trace("Successfully retrieved location");
		
		return l;
	}

	public ArrayList<FullLocation> list(String locationName) throws URISyntaxException{
		//TODO _ cambiare come in firstLocation
		log.trace("Retrieving location list");
		JsonElement locationsAsJson = null;
		JsonElement cityAsJson = null;
		JsonElement metroAreaAsJson = null;
		
		FullLocation fullLocation = null;
		ArrayList<FullLocation> locations = new ArrayList<FullLocation>();

		search(locationName);
		
		if(!checkResponse()){
			return locations;
		}
		
//da : http://www.songkick.com/developer/location-search
		locationsAsJson = getJsonResponse().getAsJsonObject("resultPage").getAsJsonObject("results").getAsJsonArray("location");

		for(JsonElement location : locationsAsJson.getAsJsonArray() ){
			metroAreaAsJson = location.getAsJsonObject().getAsJsonObject("metroArea");
			cityAsJson = location.getAsJsonObject().getAsJsonObject("city");
			
			fullLocation = new FullLocation(new MetroArea(	
					metroAreaAsJson.getAsJsonObject().getAsJsonObject("country").getAsJsonObject("displayName").getAsString(),
					metroAreaAsJson.getAsJsonObject().getAsJsonObject("id").getAsString(),
					metroAreaAsJson.getAsJsonObject().getAsJsonObject("displayName").getAsString()),
											new City(
					cityAsJson.getAsJsonObject().getAsJsonObject("displayName").getAsString(), 
					cityAsJson.getAsJsonObject().getAsJsonObject("country").getAsJsonObject("displayName").getAsString(),
					cityAsJson.getAsJsonObject().getAsJsonObject("lat").getAsInt(), 
					cityAsJson.getAsJsonObject().getAsJsonObject("lng").getAsInt())
					);
			locations.add(fullLocation);
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
