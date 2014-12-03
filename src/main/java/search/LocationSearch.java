package search;


import http.SongkickConnector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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
		
		l = new FullLocation(	Extractor.extractMetroArea(metroAreaAsJson),
								Extractor.extractCity(cityAsJson));
	
		log.trace("Successfully retrieved location");	
	
		return l;
	}

	public ArrayList<FullLocation> list(String locationName) throws URISyntaxException{
		//TODO (CONTROLLARE) ricerca correttamente
		log.trace("Retrieving location list");
		JsonElement locationsAsJson = null;
		JsonElement cityAsJson = null;
		JsonElement metroAreaAsJson = null;
		FullLocation fullLocation = null;
		ArrayList<FullLocation> locations = new ArrayList<FullLocation>();

		search(locationName);
		
		if(!checkResponse()){
			locations = null;
		}
//da : http://www.songkick.com/developer/location-search

		locationsAsJson = getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location");
		
		JsonArray locationsArray = locationsAsJson.getAsJsonArray();
		
		for(JsonElement location : locationsArray){
		
			metroAreaAsJson = location.getAsJsonObject().getAsJsonObject("metroArea");
			cityAsJson = location.getAsJsonObject().getAsJsonObject("city");
			
			fullLocation = new FullLocation(Extractor.extractMetroArea(metroAreaAsJson),
											Extractor.extractCity(cityAsJson));
			locations.add(fullLocation);
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
