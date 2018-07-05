package br.itarocha.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingApi {
    public String status;
    public String errorMessage;
    public GeocodingResult[] results;
    
	 @Override
	 public String toString() {
      return "GeocodingApi{" +
              "status='" + status + '\'' +
              ", errorMessage='" + errorMessage + '\'' +
              ", results=" + results +
              '}';
	 }	
    
}
