package br.itarocha.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {
	
	public Geometry(){}
	
	public Geometry(LatLng location){
		this.location = location;
	}
	
	private LatLng location;

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}
	
	 @Override
	 public String toString() {
       return "Geometry{" +
               "location='" + location +
               '}';
	 }	
	

}
