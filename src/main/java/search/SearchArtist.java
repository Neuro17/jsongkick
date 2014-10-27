package search;

import http.SongkickConnector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.utils.URIBuilder;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import config.SongkickConfig;
import entity.SongkickArtist;

public class SearchArtist extends SongkickConnector {
	private static final Logger log = Logger.getLogger(Class.class.getName());
	
	public SearchArtist(){
		super.gson = new GsonBuilder().setPrettyPrinting().create();
		super.uriBld = new URIBuilder();
		log.setLevel(Level.INFO);
	}
	
	/**
	 * Extracts the first artist received by songkick response.
	 * @throws URISyntaxException 
	 */
	public SongkickArtist firstArtist(String artistName) throws URISyntaxException{
		String name;
		String id;
		
		log.info("Retrieving first artist");
		
		buildURI();
		
		uri = query(artistName);
		
		search(uri);
		
		if(!isEmptyResponse()){
			
			JsonElement firstArtist = getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("artist").get(0);
			
			name = firstArtist.getAsJsonObject().get("displayName").getAsString();
			id = firstArtist.getAsJsonObject().get("id").getAsString();
			log.info(name + " " + id);
			log.info("Successfully retrieved artist");

//			System.out.println(gson.toJson(firstArtist));
			log.info(gson.toJson(firstArtist));
			
			return new SongkickArtist(name, id);

		}
		else {
			log.warning("Artist not found");
		}
		return new SongkickArtist();
	}
	
	private URI query(String artistName) throws URISyntaxException{
		return uriBld.setParameter("query", artistName).setParameter("apikey", SongkickConfig.getApiKey()).build();
	}
}
