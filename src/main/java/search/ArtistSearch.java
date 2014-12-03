package search;

import http.SongkickConnector
;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import config.SongkickConfig;
import entity.Artist;

public class ArtistSearch extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(ArtistSearch.class);
	
	private Artist artist;
	
	public ArtistSearch(){
		gson = new GsonBuilder().setPrettyPrinting().create();
		uriBld = new URIBuilder();
		artist = new Artist();
	}
	
	public void search(String artistName) throws URISyntaxException{
		buildURI();
		
		uri = query(artistName);
		
		executeRequest(uri);
	}
		
	/**
	 * Extracts the first artist received by songkick response.
	 * @throws URISyntaxException 
	 */
	public Artist firstArtist(String artistName) throws URISyntaxException{
		String name;
		String id;
		JsonElement firstArtist = null;
		
		log.trace("Retrieving first artist");
		
		search(artistName);
		
		if(!checkResponse()){
			artist = null;
		}
	
		firstArtist = getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("artist").get(0);
		
		name = firstArtist.getAsJsonObject().get("displayName").getAsString();
		id = firstArtist.getAsJsonObject().get("id").getAsString();
		
		log.trace("Successfully retrieved artist");
		
		return artist = new Artist(name, id);
		
	}
	
	public ArrayList<Artist> list(String artistName) throws URISyntaxException{
		log.trace("Retrieving artists list");
		JsonElement artistsAsJson = null;
		ArrayList<Artist>  artists = new ArrayList<Artist>();
		
		search(artistName);
		
		if(!checkResponse()){
			return artists;
		}
			
		artistsAsJson = getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("artist");
		
		for(JsonElement artist : artistsAsJson.getAsJsonArray() ){
			
			log.debug(gson.toJson(artist.getAsJsonObject().get("displayName").getAsString()));
			log.debug(gson.toJson(artist.getAsJsonObject().get("id").getAsString()));
			
			artists.add(new Artist(	artist.getAsJsonObject().get("displayName").getAsString(), 
									artist.getAsJsonObject().get("id").getAsString()));
		}
		
		log.trace("Successfully retrieved artists");
		return artists;
	}
	
	@Override
	public URI query(String artistName) throws URISyntaxException{
		return uriBld.setPath(SongkickConfig.getArtistPath()).setParameter("query", artistName).setParameter("apikey", SongkickConfig.getApiKey()).build();
	}
	
}