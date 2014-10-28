package jsongkick;

import java.io.IOException;
import java.net.URISyntaxException;
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
	}
}
