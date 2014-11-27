package search;


import http.SongkickConnector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;

import config.SongkickConfig;
import entity.FullLocation;

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
	
	public String firstLocation(String locationName) throws URISyntaxException{
		//TODO - Cambiare il valore di ritorno in Location
		log.trace("Retrieving location id");
		String id;
		
		search(locationName);
		
		id = checkResponse() ? getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0).getAsJsonObject().getAsJsonObject("metroArea").get("id").getAsString() : null;
			
//		id = getJsonResponse().getAsJsonObject().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0).getAsJsonObject().getAsJsonObject("metroArea").get("id").getAsString();
		
		log.trace("Successfully retrieved location id");
		return id;
	}
	
	public ArrayList<FullLocation> list(String locationName){
		//TODO - Cambiare il valore di ritorno in Location 
		return null;
	}
	
	@Override
	public URI query(String locationName) throws URISyntaxException{
		return uriBld.setPath(SongkickConfig.getLocationPath()).setParameter("query", locationName).setParameter("apikey", SongkickConfig.getApiKey()).build();
	}

}
