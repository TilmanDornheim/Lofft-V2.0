package lofft.lofftv20.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tilma on 2017-07-20.
 */

public class User implements Serializable{

	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String picture;
	private ArrayList<String> tags;
	private float rating;
	private Category favorite;
	private boolean star;
	private Location homeLocation;

	public User(int id, String username, String firstName,String lastName,String picture, ArrayList<String> tags,float rating, Category favorite, boolean star,Location homeLocation) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.picture = picture;
		this.tags = tags;
		this.rating = rating;
		this.favorite = favorite;
		this.star = star;
		this.homeLocation = homeLocation;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName(){ return lastName;}

	public String getPicture(){ return picture;}

	public ArrayList<String> getTags() {
		return tags;
	}

	public Float getRating(){ return rating;}

	public Category getFavorite(){ return favorite;}

	public Boolean getStar(){ return star;}

	public Location getHomeLocation(){return homeLocation;}
}
