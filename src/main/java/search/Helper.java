package search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import entity.Artist;
import entity.Concert;
import entity.Location;
import entity.MetroArea;
import entity.Venue;

public class Helper {
	
	private static final Logger log = LogManager.getLogger(Helper.class);

	public static Concert extractConcert(JsonElement item){
		Concert event = new Concert();
		JsonObject concertTmp = item.getAsJsonObject();
		Venue venueTmp;
		DateTime datetime;
		MetroArea metroAreaTmp;
		LocalDate localDate;
		
//		log.debug(concertTmp.toString());
		event.setPopularity(concertTmp.get("popularity").getAsDouble());
		event.setId(concertTmp.get("id").getAsString());
		
//		TODO - Implementare DateFormatter usando il pattern per la stringa da parsare.
		
//		log.debug("datetime null? "+ concertTmp.getAsJsonObject("start").get("datetime").isJsonNull());
		
		datetime = concertTmp.getAsJsonObject("start").get("datetime").isJsonNull() ? null : new DateTime(concertTmp.getAsJsonObject("start").get("datetime").getAsString());
		
		localDate = new LocalDate(concertTmp.getAsJsonObject("start").get("date").getAsString());
		
		//TODO - verificare perchè a volte viene creata una data corrente invece che la data del concerto reale
		
		event.setDateTime(new DateTime(datetime));
		event.setDate(localDate);		
		event.setLocation(extractLocation(concertTmp.get("location")));
		
		metroAreaTmp = extractMetroArea(concertTmp.getAsJsonObject("venue").getAsJsonObject("metroArea"));
		
//		log.debug(concertTmp.getAsJsonObject("venue"));
		
//		log.debug(concertTmp.getAsJsonObject("venue").get("lat"));
		if(!concertTmp.getAsJsonObject("venue").get("id").isJsonNull()){
			venueTmp = new Venue(metroAreaTmp,
				concertTmp.getAsJsonObject("venue").get("id").getAsString(),
				concertTmp.getAsJsonObject("venue").get("displayName").getAsString());
			
			event.setVenue(venueTmp);
		}
		
//		log.debug(event.toString());
		
		return event;
	}
	
	public static Location extractLocation(JsonElement item){
		Double lat, lng;
		Location location;
		JsonObject locTmp = item.getAsJsonObject();
		
		lat = locTmp.get("lat").isJsonNull() ? null : locTmp.get("lat").getAsDouble();
		lng = locTmp.get("lng").isJsonNull() ? null : locTmp.get("lng").getAsDouble();
		
		if(lat == null && lng == null){
			location = new Location(locTmp.get("city").getAsString());
		}
		else {
			location = new Location(lat, lng, locTmp.get("city").getAsString());
		}
		return location;
	}

	public static Venue extractVenue(JsonElement item){
		//TODO
		return null;
	}
	
	public static MetroArea extractMetroArea(JsonElement item){
		MetroArea metroArea;
		JsonObject metroAreaTmp = item.getAsJsonObject();
		
		metroArea = new MetroArea(metroAreaTmp.getAsJsonObject("country").get("displayName").getAsString(),
				metroAreaTmp.get("id").getAsString(),
				metroAreaTmp.get("displayName").getAsString());
		
		return metroArea;
	}
	
	public static Artist extractArtist(JsonElement item){
		//TODO
		return null;
	}
	
}
