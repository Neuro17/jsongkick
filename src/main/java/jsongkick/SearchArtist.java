package jsongkick;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.apache.http.client.utils.URIBuilder;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class SearchArtist extends Search {
	private static final Logger log = Logger.getLogger(Class.class.getName());
	protected static final String PATH = "/search/artists.json";
	private URI uri;
	private URIBuilder uriBld;
	
	public SearchArtist(){
		uriBld = new URIBuilder();
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	public void buildURI(String artistName) throws URISyntaxException{
		log.info("Building URI");
		
		uri = uriBld.setScheme(SCHEME)
				.setHost(HOST)
				.setPath(PATH)
				.setParameter("query", artistName)
				.setParameter("apikey", API_KEY)
				.build();
		
		log.info("Succesfully build: " + uri); 
	}
	
	public void firstArtist(){
		log.info("Retrieving first artist");

		if(!isEmptyResponse()){
			JsonElement firstArtist = getJsonObj().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("artist").get(0);

			log.info("Successfully retrieved artist");

			System.out.println(gson.toJson(firstArtist));

		}
		else {
			log.warning("Artist not found");
		}
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}
}
