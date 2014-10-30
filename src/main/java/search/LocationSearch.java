package search;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import config.SongkickConfig;
import entity.SongkickArtist;
import http.SongkickConnector;

public class LocationSearch extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(LocationSearch.class);
	
	public LocationSearch() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		uriBld = new URIBuilder();
	}
	
	public void search(String artistName) throws URISyntaxException{
		buildURI();
		
		uri = query(artistName);
		
		executeRequest(uri);
	}
	
	public String findId(String locationName) throws URISyntaxException{
		log.trace("Retrieving location id");
		String id;
		
		search(locationName);
			
		id = getJsonResponse().getAsJsonObject().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0).getAsJsonObject().getAsJsonObject("metroArea").get("id").getAsString();
		
		log.trace("Successfully retrieved location id");
		return id;
	}
	
	@Override
	public URI query(String locationName) throws URISyntaxException{
		return uriBld.setPath(SongkickConfig.getLocationPath()).setParameter("query", locationName).setParameter("apikey", SongkickConfig.getApiKey()).build();
	}

}
