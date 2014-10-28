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
import entity.SongkickArtist;

public class SearchArtist extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(SearchArtist.class);
	
	public SearchArtist(){
		super.gson = new GsonBuilder().setPrettyPrinting().create();
		super.uriBld = new URIBuilder();
	}
	
	public void search(String artistName) throws URISyntaxException{
		buildURI();
		
		uri = query(artistName);
		
		executeRequest(uri);
	}
	
	public boolean checkResponse(){
		if(super.isNullResponse()){
			log.error("Timeout scaduto");
			return false;
		}
			
		if(isEmptyResponse()){
			log.error("Artists not found");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Extracts the first artist received by songkick response.
	 * @throws URISyntaxException 
	 */
	public SongkickArtist firstArtist(String artistName) throws URISyntaxException{
		String name;
		String id;
		JsonElement firstArtist = null;
		
		log.trace("Retrieving first artist");
		
		search(artistName);
		
		if(!checkResponse()){
			return new SongkickArtist();
		}
	
		firstArtist = super.getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("artist").get(0);
		
		name = firstArtist.getAsJsonObject().get("displayName").getAsString();
		id = firstArtist.getAsJsonObject().get("id").getAsString();
		
		log.trace("Successfully retrieved artist");
		
		return new SongkickArtist(name, id);
		
	}
	
	public ArrayList<SongkickArtist> list(String artistName) throws URISyntaxException{
		log.trace("Retrieving artists list");
		JsonElement artistsAsJson = null;
		ArrayList<SongkickArtist>  artists = new ArrayList<SongkickArtist>();
		
		search(artistName);
		
		if(!checkResponse()){
			return artists;
		}
			
		artistsAsJson = super.getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("artist");
		
		for(JsonElement artist : artistsAsJson.getAsJsonArray() ){
			log.debug(gson.toJson(artist.getAsJsonObject().get("displayName").getAsString()));
			log.debug(gson.toJson(artist.getAsJsonObject().get("id").getAsString()));
			artists.add(new SongkickArtist(artist.getAsJsonObject().get("displayName").getAsString(), artist.getAsJsonObject().get("id").getAsString()));
		}
		
		log.trace("Successfully retrieved artists");
		return artists;
	}
	
	private URI query(String artistName) throws URISyntaxException{
		return uriBld.setParameter("query", artistName).setParameter("apikey", SongkickConfig.getApiKey()).build();
	}
	
	public boolean isEmptyResponse(){
		return super.getJsonResponse().getAsJsonObject("resultsPage").get("totalEntries").getAsInt() == 0;
	}
}