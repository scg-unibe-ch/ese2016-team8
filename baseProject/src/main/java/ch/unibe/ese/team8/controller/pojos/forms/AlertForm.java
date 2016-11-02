package ch.unibe.ese.team8.controller.pojos.forms;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import ch.unibe.ese.team8.model.User;

/** This form is used when a user wants to create a new alert. */
public class AlertForm {

	private User user;

	@NotBlank(message = "Required")
	@Pattern(regexp = "^[0-9]{4} - [-\\w\\s\\u00C0-\\u00FF]*", message = "Please pick a city from the list")
	private String city;

	@NotNull(message = "Requires a number")
	@Min(value = 0, message = "Please enter a positive distance")
	private Integer radius;

	@NotNull(message = "Requires a number")
	@Min(value = 0, message = "In your dreams.")
	private Integer price;

	private int zipCode;

	@AssertFalse(message = "Please select either or both types")
	private boolean noRoomNoStudio;

	@NotNull(message = "Requires a category")
	private String category;

	private boolean houseHelper;
	private boolean roomHelper;
	private boolean studioHelper;
	
	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(final int zip) {
		this.zipCode = zip;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(final Integer radius) {
		this.radius = radius;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(final Integer price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public String getCategory() {
		return category;
	}

	// TODO: change dependencies where below 'getCategory(...)' is used to 'setCategory(...)'
	public void setCategory(final String category) {
		this.category = category;
	}

	public boolean getHouseHelper() {
		return houseHelper;
	}

	public void setHouseHelper(boolean houseHelper) {
		this.houseHelper = houseHelper;
	}

	public boolean getRoomHelper() {
		return roomHelper;
	}

	public void setRoomHelper(boolean roomHelper) {
		this.roomHelper = roomHelper;
	}

	public boolean getStudioHelper() {
		return studioHelper;
	}

	public void setStudioHelper(boolean studioHelper) {
		this.studioHelper = studioHelper;
	}
}
