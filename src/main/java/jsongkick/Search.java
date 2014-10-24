package jsongkick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class Search {
	private static final Logger log = Logger.getLogger(Class.class.getName());;
	protected static final String SCHEME = "http";
	protected static final String API_KEY = "iF1N0jYrhI5wtG3n";
	protected static final String HOST = "api.songkick.com/api/3.0";	
	protected Gson gson;
	private CloseableHttpClient httpClient;
	private JsonObject jsonObj;
	

	/**
	 * Builds a string representing response from songkick ready to be parse as JSON.
	 * 
	 * @param response					HttpRespose received by HttpGet call.	
	 * @return StringBuffer				String representing the response. Ready to parse as JSON.
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected StringBuffer parseResponse(HttpResponse response) throws IllegalStateException, IOException{
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		StringBuffer result = new StringBuffer();
		
		String line = "";
		
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}
		
		return result;
	}
	
	public void openConnection(){
		log.info("Opening connection");

		httpClient = HttpClients.createDefault();
	}
	
	/**
	 * @throws IOException
	 */
	public void closeConnection() throws IOException{
		log.info("Closing connection");

		httpClient.close();
	}
	
	/**
	 * Performs HTTP get to songkick given a valid URI, returns JSON object representation of response.
	 * 
	 * @param uri
	 * @return JsonObject
	 */
	public JsonObject search(URI uri){
		HttpResponse response;
		StringBuffer result;
		JsonParser jsonParser = new JsonParser();
		HttpGet httpget = new HttpGet(uri);
				
		try {
			response = httpClient.execute(httpget);
			
			result = this.parseResponse(response);
			
			jsonObj = jsonParser.parse(result.toString()).getAsJsonObject();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObj;
	}
	
	public boolean isEmptyResponse(){
		return jsonObj.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() == 0;
	}

	public JsonObject getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(JsonObject jsonObj) {
		this.jsonObj = jsonObj;
	}
}