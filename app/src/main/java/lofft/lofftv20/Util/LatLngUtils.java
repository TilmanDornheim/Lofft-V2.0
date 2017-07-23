package lofft.lofftv20.Util;

import com.google.android.gms.maps.model.LatLng;

import lofft.lofftv20.Models.Location;

/**
 * Created by tilma on 2017-07-22.
 */

public class LatLngUtils {

	public static final LatLng londonl = new LatLng(51.509865,-0.118092);
	public static final LatLng venicel = new LatLng(45.444958,	12.328463);
	public static final LatLng leverkusenl = new LatLng(51.0303, 6.98432);
	public static final LatLng moronMountainl = new LatLng(9.072264, 7.491302);

	public static final Location london = new Location("London",londonl);
	public static final Location venice = new Location("Venice",venicel);
	public static final Location leverkusen = new Location("Leverkusen",leverkusenl);
	public static final Location moronMountain = new Location("Moron Mountain",moronMountainl);

}
