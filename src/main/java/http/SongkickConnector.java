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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.SongkickConfig;

public abstract class SongkickConnector implements HttpConnector{
	private static final Logger log = LogManager.getLogger(SongkickConnector.class);
	private JsonObject jsonResponse;
	protected Gson gson;
	protected URIBuilder uriBld;
	protected URI uri;
	protected URL url;
	

	/**
	 * Builds a string representing response from songkick ready to be parse as JSON.
	 * 
	 * @param response					HttpRespose received by HttpGet call.	
	 * @return StringBuffer				String representing the response. Ready to parse as JSON.
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public JsonObject parseResponseAsJson(InputStream response) throws IllegalStateException, IOException{
		log.trace("Entering parseResponseAsJson");
		BufferedReader rd = new BufferedReader(new InputStreamReader(response));
		
		JsonParser jsonParser = new JsonParser();
		
		StringBuffer result = new StringBuffer();
		
		String line = "";
		
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}
		log.trace("Exiting parseResponseAsJson");
		return jsonParser.parse(result.toString()).getAsJsonObject();
	}
	
	/**
	 * Performs HTTP get to songkick given a valid URI, returns JSON object representation of response.
	 * 
	 * @param uri
	 * @return JsonObject
	 */
	public JsonObject executeRequest(URI uri){
		InputStream response;
		
		try {
			url = uri.toURL();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
				
		try {
			response = url.openStream();
			
			log.debug(response.toString());
			log.debug(uri.toString());
			
			jsonResponse = this.parseResponseAsJson(response);
			
			log.debug(jsonResponse.toString());
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			
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