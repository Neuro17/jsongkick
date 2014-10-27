package jsongkick;

import java.io.IOException;
import java.net.URISyntaxException;

import entity.SongkickArtist;
import search.SearchArtist;

public class App {

	public static void main(String[] args) throws IOException, URISyntaxException {
		SongkickArtist artist;
		SearchArtist sa = new SearchArtist();
		
		sa.openConnection();
		
		artist = sa.firstArtist("Queens of the stone age");
		
		System.out.println(artist.toString());
		
		sa.closeConnection();
	}
}
