package jsongkick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.SongkickArtist;
import search.SearchArtist;

public class App {
	
	private static final Logger log = LogManager.getLogger(App.class);
	
	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		ArrayList <SongkickArtist> artists = new ArrayList<SongkickArtist>();
		SearchArtist sa = new SearchArtist();
		
		sa.openConnection();
		
		artists = sa.list("pearl jam");
		log.info(sa.firstArtist("nirvana"));
		
		for(SongkickArtist artist : artists){
			log.info(artist.toString());
		}
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("312553");
		arr.add("7903054");
		arr.add("7947163");
		for(String id : arr){
			URL url = new URL("http://api.songkick.com/api/3.0/artists/"+id+"/gigography.json?apikey=iF1N0jYrhI5wtG3n");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			
			StringBuffer result = new StringBuffer();
			
			String line = "";
			
			while ((line = in.readLine()) != null) {
			    result.append(line);
			}
			
			log.debug(result);
		}
		
			
	}
}
