package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.SongkickConfig;

public abstract class SongkickConnector implements HttpConnector{
	private static final Logger log = LogManager.getLogger(SongkickConnector.class);
	private JsonObject jsonResponse;
	protected Gson gson = new GsonBuilder().setPrettyPrinting().create();
	protected URIBuilder uriBld;
	protected URI uri;
	protected URL url;
	

	/**
	 * Builds a string representing response from songkick ready to be parse as JSON.
	 * 
	 * @param  response					HttpRespose received by HttpGet call.	
	 * @return StringBuffer				String representing the response. Ready to parse as JSON.
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public JsonObject parseResponseAsJson(InputStream response){
		log.trace("Entering parseResponseAsJson");
		BufferedReader rd = new BufferedReader(new InputStreamReader(response));
		
		JsonParser jsonParser = new JsonParser();
		
		StringBuffer result = new StringBuffer();
		
		String line = "";
		
		try {
			while ((line = rd.readLine()) != null) {
			    result.append(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		log.trace("Exiting parseResponseAsJson");
		return jsonParser.parse(result.toString()).getAsJsonObject();
	}
	
	/**
	 * Performs HTTP get given a valid URI, returns JSON object representation of response.	
	 * 
	 * @param uri
	 * @return JsonObject
	 */
	public JsonObject executeRequest(URI uri){
		//TODO - capire come accedere ai risultati che sono oltre la prima pagina. 
		InputStream response;
		
		try {
			url = uri.toURL();
		} catch (MalformedURLException e1) {
			
		}
				
		try {
			response = url.openStream();
			
			log.debug(response.toString());
			log.debug(uri.toString());
			
			jsonResponse = this.parseResponseAsJson(response);
			
			log.debug("Number of entries: " + jsonResponse.getAsJsonObject("resultsPage").get("totalEntries").getAsString());
			log.debug("Number of results per page: " + jsonResponse.getAsJsonObject("resultsPage").get("perPage").getAsString());
			log.debug("Page number: " + jsonResponse.getAsJsonObject("resultsPage").get("page"));
			
//			log.debug(gson.toJson(jsonResponse));
			
		} catch (ClientProtocolException e) {
			log.error(e.getMessage());
			
		} catch (IOException e) {
			log.error(e.getMessage());
			jsonResponse =  null;
		}
		
		return jsonResponse;
	}
	
	public boolean isNullResponse(){
		return jsonResponse == null;
	}
	
	public boolean isEmptyResponse(){
		return jsonResponse.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() == 0;
	}

	public JsonObject getJsonResponse() {
		return jsonResponse;
	}
	
	public boolean checkResponse(){
		if(isNullResponse()){
			log.error("Timeout scaduto");
			return false;
		}
			
		if(isEmptyResponse()){
			log.error("Resource not found");
			return false;
		}
		
		return true;
	}
	
	public void buildURI(){
		log.trace("Building URI");
		
		uriBld.setScheme(SongkickConfig.getScheme()).setHost(SongkickConfig.getHost());		
		
		log.trace("Succesfully build:"); 
	}
	
	public abstract URI query(String param) throws URISyntaxException;
}