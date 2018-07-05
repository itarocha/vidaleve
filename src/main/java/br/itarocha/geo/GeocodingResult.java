package br.itarocha.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingResult {
	
	//public AddressComponent[] addressComponents;
	public Geometry geometry;
	public String formattedAddress;

	
	 @Override
	 public String toString() {
       return "GeocodingResult{" +
               ", formatted_address='" + formattedAddress + '\'' +
               ", geometry=" + geometry +
               '}';
	 }	
	

}
