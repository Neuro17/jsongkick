package http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;

import com.google.gson.JsonObject;

public interface HttpConnector {
	
	public void openConnection();

//	public void closeConnection();
	
	public JsonObject parseResponseAsJson(HttpResponse response) throws IllegalStateException, IOException;
	
	public boolean isEmptyResponse();
	
	public JsonObject executeRequest(URI uri);
	
	public void buildURI() throws URISyntaxException;

}

