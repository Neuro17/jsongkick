package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.SongkickConfig;

public abstract class SongkickConnector implements HttpConnector{
	private static final Logger log = LogManager.getLogger(SongkickConnector.class);
	protected Gson gson;
	private HttpClient httpClient;
//	private RequestConfig reqConfig;
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
		log.trace("Entering praseResponseAsJson");
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		JsonParser jsonParser = new JsonParser();
		
		StringBuffer result = new StringBuffer();
		
		String line = "";
		
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}
		log.trace("Exiting praseResponseAsJson");
		return jsonParser.parse(result.toString()).getAsJsonObject();
	}
	
	public RequestConfig createConfig(){
		int timeout = 10000;
		RequestConfig reqConfig = RequestConfig.custom()
			    .setSocketTimeout(timeout)
			    .setConnectTimeout(timeout)
			    .setConnectionRequestTimeout(timeout)
			    .build();
		
		return reqConfig;
	}
	
	public void openConnection(){
		log.trace("Opening connection");

		httpClient = new ApacheHttpTransport().getHttpClient();	    
	}
	
	/**
	 * @throws IOException
	 */
//	public void closeConnection(){
//		log.info("Closing connection");
//
//		try {
//			httpClient.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * Performs HTTP get to songkick given a valid URI, returns JSON object representation of response.
	 * 
	 * @param uri
	 * @return JsonObject
	 */
	public JsonObject executeRequest(URI uri){
		HttpResponse response;
		
		HttpGet httpget = new HttpGet(uri);
				
		try {
			response = httpClient.execute(httpget);
			
			log.debug(response.toString());
			log.debug(uri.toString());
			
			jsonResponse = this.parseResponseAsJson(response);
			
			log.debug(jsonResponse.toString());
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			jsonResponse =  null;
		}
		
		
		return jsonResponse;
	}
	
	public boolean isNullResponse(){
		return jsonResponse == null;
	}

	public JsonObject getJsonResponse() {
		return jsonResponse;
	}
	
	public void buildURI(){
		log.trace("Building URI");
		
		uriBld.setScheme(SongkickConfig.getScheme())
				.setHost(SongkickConfig.getHost())
				.setPath(SongkickConfig.getArtistPath());				
		
		log.trace("Succesfully build:"); 
	}
}