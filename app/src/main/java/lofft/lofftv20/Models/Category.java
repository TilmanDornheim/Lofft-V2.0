package lofft.lofftv20.Models;

/**
 * Created by tilma on 2017-07-20.
 */

public class Category {

	private String name;
	private String color;
	private int drawable;
	private boolean selected = false;

	public Category(String name, String color,int drawable) {
		this.name = name;
		this.color = color;
		this.drawable = drawable;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public void setSelected(boolean selected){

		this.selected = selected;


	}
	public boolean getSelected(){

		return selected;

	}

	public int getDrawable(){ return drawable;}


	@Override
	public String toString(){


		return name;

	}
}
