package search;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import config.SongkickConfig;
import http.SongkickConnector;

public class EventSearch extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(EventSearch.class);

	public EventSearch() {
		uriBld = new URIBuilder();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	@Override
	public URI query(String id) throws URISyntaxException {
		log.trace("setting parameters to query");
		
		return uriBld.setPath(SongkickConfig.getEventPath()).setCustomQuery("location=sk:"+id+"&apikey="+SongkickConfig.getApiKey()).build();	
	}
	
	public JsonObject eventsList(String id) throws URISyntaxException{
		log.trace("entering eventList");
		JsonObject events = new JsonObject();
		
		buildURI();
		
		uri = query(id);
		
		log.debug("uri built: " + uri);
		
		executeRequest(uri);
		
		events = getJsonResponse();
		log.trace("exiting eventList");
		return events;
	}

}