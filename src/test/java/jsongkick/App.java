package jsongkick;

import java.io.IOException;
import java.net.URISyntaxException;

import search.SearchArtist;

public class App {

	public static void main(String[] args) throws IOException, URISyntaxException {
		SearchArtist sa = new SearchArtist();
		
		sa.openConnection();
		
		sa.buildURI("Nirvana");
		
		sa.search(sa.getUri());
		
		sa.firstArtist();
		
		sa.closeConnection();
	}
}
