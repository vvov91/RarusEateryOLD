package rarus.vovchenko.eatery;

/**
 * Объект меню блюдо
 * 
 * @author Victor Vovchenko <v.vovchenko91@gmail.com>
 *
 */
public class Dish {
	private int mId;
	private String mName;
	private String mDescription;
	private boolean mPortioned;
	private float mPrice;
	private String mRating;
	private float mAvailableAmmount;
	private float mOrderedAmmount;
	
	public int getId() {
		return mId;
	}
	public void setId(int mId) {
		this.mId = mId;
	}
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}
	public boolean isPortioned() {
		return mPortioned;
	}
	public void setPortioned(boolean mPortioned) {
		this.mPortioned = mPortioned;
	}
	public float getPrice() {
		return mPrice;
	}
	public void setPrice(float mPrice) {
		this.mPrice = mPrice;
	}
	public String getRating() {
		return mRating;
	}
	public void setRating(String mRating) {
		this.mRating = mRating;
	}
	public float getAvailableAmmount() {
		return mAvailableAmmount;
	}
	public void setAvailableAmmount(float mAvailableAmmount) {
		this.mAvailableAmmount = mAvailableAmmount;
	}
	public float getOrderedAmmount() {
		return mOrderedAmmount;
	}
	public void setOrderedAmmount(float mOrderedAmmount) {
		this.mOrderedAmmount = mOrderedAmmount;
	}	
}