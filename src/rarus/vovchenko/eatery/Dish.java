package rarus.vovchenko.eatery;

/**
 * Объект меню блюдо
 * 
 * @author Victor Vovchenko <v.vovchenko91@gmail.com>
 *
 */
public class Dish {
	private int _id;
	private String name;
	private String description;
	private boolean portioned;
	private float price;
	private String rating;
	private float available_ammount;
	private float ordered_ammount;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPortioned() {
		return portioned;
	}
	public void setPortioned(boolean portioned) {
		this.portioned = portioned;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public float getAvailable_ammount() {
		return available_ammount;
	}
	public void setAvailable_ammount(float availbale_ammount) {
		this.available_ammount = availbale_ammount;
	}
	public float getOrdered_ammount() {
		return ordered_ammount;
	}
	public void setOrdered_ammount(float ordered_ammount) {
		this.ordered_ammount = ordered_ammount;
	}
}