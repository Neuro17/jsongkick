package entity;

public class MetroArea {
	private String country;
	private String id;
		
	public MetroArea(String country, String id) {
		super();
		this.country = country;
		this.id = id;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
