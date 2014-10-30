package entity;
public class Venue {
	
	private double latitude;
	private double longitude;
	private MetroArea metro;
	private String id;
	private String displayName;
	
	public Venue(double latitude, double longitude, MetroArea metro, String id, String displayName) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.metro = metro;
		this.id = id;
		this.displayName = displayName;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public MetroArea getMetro() {
		return metro;
	}
	public void setMetro(MetroArea metro) {
		this.metro = metro;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	
}
