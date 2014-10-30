package entity;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class Concert {
	private DateTime dateTime; 
	private ArrayList<SongkickArtist> performance;
	private Venue venue;
	private String id;
	private int popularity;
	private Location location;
	
	public Concert(DateTime dateTime, ArrayList<SongkickArtist> performance,
			Venue venue, String id, int popularity, Location location) {
		super();
		this.dateTime = dateTime;
		this.performance = performance;
		this.venue = venue;
		this.id = id;
		this.popularity = popularity;
		this.location = location;
	}
	public DateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	public ArrayList<SongkickArtist> getPerformance() {
		return performance;
	}
	public void setPerformance(ArrayList<SongkickArtist> performance) {
		this.performance = performance;
	}
	public Venue getVenue() {
		return venue;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}
