package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.SongkickConfig;

public abstract class SongkickConnector implements HttpConnector{
	private static final Logger log = Logger.getLogger(Class.class.getName());
	protected Gson gson;
	private CloseableHttpClient httpClient;
	private JsonObject jsonResponse;
	protected URIBuilder uriBld;
	protected URI uri;
	

	/**
	 * Builds a string representing response from songkick ready to be parse as JSON.
	 * 
	 * @param response					HttpRespose received by HttpGet call.	
	 * @return StringBuffer				String representing the response. Ready to parse as JSON.
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public JsonObject parseResponseAsJson(HttpResponse response) throws IllegalStateException, IOException{
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		JsonParser jsonParser = new JsonParser();
		
		StringBuffer result = new StringBuffer();
		
		String line = "";
		
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}
		
		return jsonParser.parse(result.toString()).getAsJsonObject();
	}
	
	public void openConnection(){
		log.info("Opening connection");

		httpClient = HttpClients.createDefault();
	}
	
	/**
	 * @throws IOException
	 */
	public void closeConnection(){
		log.info("Closing connection");

		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Performs HTTP get to songkick given a valid URI, returns JSON object representation of response.
	 * 
	 * @param uri
	 * @return JsonObject
	 */
	public JsonObject search(URI uri){
		HttpResponse response;
		
		HttpGet httpget = new HttpGet(uri);
				
		try {
			response = httpClient.execute(httpget);
			
			jsonResponse = this.parseResponseAsJson(response);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonResponse;
	}
	
	public boolean isEmptyResponse(){
		return jsonResponse.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() == 0;
	}

	public JsonObject getJsonResponse() {
		return jsonResponse;
	}
	
	public void buildURI(){
		log.info("Building URI");
		
		uriBld.setScheme(SongkickConfig.getScheme())
				.setHost(SongkickConfig.getHost())
				.setPath(SongkickConfig.getArtistPath());				
		
		log.info("Succesfully build:"); 
	}
}