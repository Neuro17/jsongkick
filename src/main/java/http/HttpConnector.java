package http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonObject;

public interface HttpConnector {
	
//	public void openConnection();

//	public void closeConnection();
	
	public JsonObject parseResponseAsJson(InputStream response) throws IllegalStateException, IOException;
	
	public boolean isEmptyResponse();
	
	public JsonObject executeRequest(URI uri);
	
	public void buildURI() throws URISyntaxException;
	
//	public URI query(String param) throws URISyntaxException;
	
	public boolean checkResponse();
	
	public boolean isNullResponse();

}

