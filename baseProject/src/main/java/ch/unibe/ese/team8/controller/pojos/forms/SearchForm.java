package ch.unibe.ese.team8.controller.pojos.forms;

import java.util.ArrayList;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/** This form is used for searching for an ad. */
public class SearchForm {

	private boolean filtered;

	private boolean sale;

	@NotBlank(message = "Required")
	@Pattern(regexp = "^[0-9]{4} - [-\\w\\s\\u00C0-\\u00FF;]*", message = "Please pick a city from the list")
	private String city;

	@NotNull(message = "Requires a number")
	@Min(value = 0, message = "Please enter a positive distance")
	private Integer radius;

	@NotNull(message = "Requires a number")
	@Min(value = 0, message = "In your dreams.")
	private Integer prize;

	@AssertFalse(message = "Please select either or both types")
	private boolean noCategory;

	@NotNull(message = "Requires a category")
	private String category = "";

	private boolean bothRentAndSale;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getPrize() {
		return prize;
	}

	public void setPrize(Integer prize) {
		this.prize = prize;
	}

	public boolean getSale() {
		return sale;
	}

	public void setSale(boolean sale) {
		this.sale = sale;
	}

	public boolean getNoCategory() {
		return noCategory;
	}

	public void setNoCategory(boolean noCategory) {
		this.noCategory = noCategory;
	}

	public boolean getBothRentAndSale() {
		return bothRentAndSale;
	}

	public void setBothRentAndSale(boolean bothRentAndSale) {
		this.bothRentAndSale = bothRentAndSale;
	}

	// //////////////////
	// Filtered results//
	// //////////////////

	public boolean getFiltered() {
		return filtered;
	}

	public void setFiltered(boolean filtered) {
		this.filtered = filtered;
	}

	private String earliestMoveInDate;
	private String latestMoveInDate;
	private String earliestMoveOutDate;
	private String latestMoveOutDate;

	private boolean smokers;
	private boolean animals;
	private boolean garden;
	private boolean balcony;
	private boolean cellar;
	private boolean furnished;
	private boolean cable;
	private boolean garage;
	private boolean internet;

	private boolean roomHelper;

	// the ugly stuff
	private boolean studioHelper;

	private boolean houseHelper;
	private boolean saleHelper;
	private boolean rentHelper;

	public boolean getSmokers() {
		return smokers;
	}

	public void setSmokers(boolean smokers) {
		this.smokers = smokers;
	}

	public boolean getAnimals() {
		return animals;
	}

	public void setAnimals(boolean animals) {
		this.animals = animals;
	}

	public boolean getGarden() {
		return garden;
	}

	public void setGarden(boolean hasGarden) {
		this.garden = hasGarden;
	}

	public boolean getBalcony() {
		return balcony;
	}

	public void setBalcony(boolean hasBalcony) {
		this.balcony = hasBalcony;
	}

	public boolean getCellar() {
		return cellar;
	}

	public void setCellar(boolean hasCellar) {
		this.cellar = hasCellar;
	}

	public boolean getFurnished() {
		return furnished;
	}

	public void setFurnished(boolean isFurnished) {
		this.furnished = isFurnished;
	}

	public boolean getCable() {
		return cable;
	}

	public void setCable(boolean hasCable) {
		this.cable = hasCable;
	}

	public boolean getGarage() {
		return garage;
	}

	public void setGarage(boolean hasGarage) {
		this.garage = hasGarage;
	}

	public boolean getInternet() {
		return internet;
	}

	public void setInternet(boolean hasInternet) {
		this.internet = hasInternet;
	}

	public String getEarliestMoveInDate() {
		return earliestMoveInDate;
	}

	public void setEarliestMoveInDate(String earliestMoveInDate) {
		this.earliestMoveInDate = earliestMoveInDate;
	}

	public String getLatestMoveInDate() {
		return latestMoveInDate;
	}

	public void setLatestMoveInDate(String latestMoveInDate) {
		this.latestMoveInDate = latestMoveInDate;
	}

	public String getEarliestMoveOutDate() {
		return earliestMoveOutDate;
	}

	public void setEarliestMoveOutDate(String earliestMoveOutDate) {
		this.earliestMoveOutDate = earliestMoveOutDate;
	}

	public String getLatestMoveOutDate() {
		return latestMoveOutDate;
	}

	public void setLatestMoveOutDate(String latestMoveOutDate) {
		this.latestMoveOutDate = latestMoveOutDate;
	}

	public boolean getStudioHelper() {
		return studioHelper;
	}

	public void setStudioHelper(boolean helper) {
		this.studioHelper = helper;
	}

	public boolean getRoomHelper() {
		return roomHelper;
	}

	public void setRoomHelper(boolean helper) {
		this.roomHelper = helper;
	}

	public boolean getHouseHelper() {
		return houseHelper;
	}

	public void setHouseHelper(boolean helper) {
		this.houseHelper = helper;
	}

	public boolean getSaleHelper() {
		return saleHelper;
	}

	public void setSaleHelper(boolean saleHelper) {
		this.saleHelper = saleHelper;
	}

	public boolean getRentHelper() {
		return rentHelper;
	}

	public void setRentHelper(boolean rentHelper) {
		this.rentHelper = rentHelper;
	}

	public ArrayList<String> getCategories() {
		if (category.isEmpty()) {
			setNoCategory(true);
		}
		String[] a = category.split(",");
		ArrayList<String> categories = new ArrayList<String>();
		for (int i = 0; i < a.length; i++) {
			categories.add(a[i].replace(",", ""));
		}
		return categories;
	}

}
