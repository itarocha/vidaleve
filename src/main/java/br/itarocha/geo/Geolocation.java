package br.itarocha.geo;

import org.springframework.web.client.RestTemplate;

public class Geolocation {

	public Geolocation(){
		
	}
	
	public static LatLng getGeoLocation(String endereco){
		LatLng retorno = new LatLng(0F,0F);
		//double lat = 0F;
		//double lng = 0F;		
		try{
			RestTemplate template = new RestTemplate();
			String uri = "http://maps.googleapis.com/maps/api/geocode/json?address="+endereco;
			System.out.println(uri);
			GeocodingApi resultado = template.getForObject(uri, GeocodingApi.class);
			if ((resultado != null) && (resultado.results.length > 0)){
				Geometry g = resultado.results[0].geometry;
				//lat = g.getLocation().getLat();
				//lng = g.getLocation().getLng();
				retorno = g.getLocation();
				//System.out.println(g);
				//pessoa.setLatitude(lat);
				//pessoa.setLongitude(lng);
			}
		} catch (Exception e){
			
		}
		
		return retorno;
	}
}
