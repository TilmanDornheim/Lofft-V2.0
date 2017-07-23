package lofft.lofftv20.Models;

/**
 * Created by tilma on 2017-07-20.
 */

public class Post {

	private int id;
	private User user;
	private String dateTimeCreated;
	private String name;
	private int price;
	private String priceDateType;
	private int duration;
	private String description;
	private Category category;
	private Location location;

	public Post(int id, User user, String dateTimeCreated, String name, int price, String priceDateType, int duration, String description, Category category, Location location) {
		this.id = id;
		this.user = user;
		this.dateTimeCreated = dateTimeCreated;
		this.name = name;
		this.price = price;
		this.priceDateType = priceDateType;
		this.duration = duration;
		this.description = description;
		this.category = category;
		this.location = location;
	}


	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getDateTimeCreated() {
		return dateTimeCreated;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getPriceDateType() {
		return priceDateType;
	}

	public int getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory(){ return category;}

	public Location getLocation() {return location;}
}
