package lofft.lofftv20.Models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by tilma on 2017-07-22.
 */

public class Location implements Serializable{

	private String name;
	private LatLng latLng;

	public Location(String name, LatLng latLng){

		this.name = name;
		this.latLng = latLng;

	}


	public String getName() {
		return name;
	}

	public LatLng getLatLng() {
		return latLng;
	}
}
