package br.itarocha.tendavisitante.util;

import br.itarocha.geo.Geolocation;
import br.itarocha.geo.LatLng;
import br.itarocha.tendavisitante.model.Endereco;

public class GeolocationBuilder {
	
	public static void resolve(Endereco endereco){
		endereco.setLatitude(0F);
		endereco.setLongitude(0F);
		String e = endereco.toString();
		LatLng latlng = Geolocation.getGeoLocation(e);
		endereco.setLatitude(latlng.getLat());
		endereco.setLongitude(latlng.getLng());
	}

}
