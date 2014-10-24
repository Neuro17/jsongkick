package jsongkick;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {

	public static void main(String[] args) throws IOException {
		SearchArtist sa = new SearchArtist();
		
		sa.openConnection();
		try {
			sa.buildURI("Nirvana");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		sa.search(sa.getUri());
		sa.firstArtist();
		sa.closeConnection();
	}
}
