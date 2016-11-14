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

	public void setCategory(final String category) {
		this.category = category;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(final Integer radius) {
		this.radius = radius;
	}

	public Integer getPrize() {
		return prize;
	}

	public void setPrize(final Integer prize) {
		this.prize = prize;
	}

	public boolean getSale() {
		return sale;
	}

	public void setSale(final boolean sale) {
		this.sale = sale;
	}

	public boolean getNoCategory() {
		return noCategory;
	}

	public void setNoCategory(final boolean noCategory) {
		this.noCategory = noCategory;
	}

	public boolean getBothRentAndSale() {
		return bothRentAndSale;
	}

	public void setBothRentAndSale(final boolean bothRentAndSale) {
		this.bothRentAndSale = bothRentAndSale;
	}

	// //////////////////
	// Filtered results//
	// //////////////////

	public boolean getFiltered() {
		return filtered;
	}

	public void setFiltered(final boolean filtered) {
		this.filtered = filtered;
	}

	private String earliestMoveInDate;
	private String latestMoveInDate;
	private String earliestMoveOutDate;
	private String latestMoveOutDate;

	private boolean allowsSmokers;
	private boolean allowsAnimals;
	private boolean hasGarden;
	private boolean hasBalcony;
	private boolean hasCellar;
	private boolean isFurnished;
	private boolean hasCable;
	private boolean hasGarage;
	private boolean hasInternet;

	private boolean roomHelper;

	// the ugly stuff
	private boolean studioHelper;

	private boolean houseHelper;
	private boolean saleHelper;
	private boolean rentHelper;

	public boolean getSmokers() {
		return allowsSmokers;
	}

	public void setSmokers(final boolean smokers) {
		this.allowsSmokers = smokers;
	}

	public boolean getAnimals() {
		return allowsAnimals;
	}

	public void setAnimals(final boolean animals) {
		this.allowsAnimals = animals;
	}

	public boolean getGarden() {
		return hasGarden;
	}

	public void setGarden(final boolean hasGarden) {
		this.hasGarden = hasGarden;
	}

	public boolean getBalcony() {
		return hasBalcony;
	}

	public void setBalcony(final boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
	}

	public boolean getCellar() {
		return hasCellar;
	}

	public void setCellar(final boolean hasCellar) {
		this.hasCellar = hasCellar;
	}

	public boolean getFurnished() {
		return isFurnished;
	}

	public void setFurnished(final boolean isFurnished) {
		this.isFurnished = isFurnished;
	}

	public boolean getCable() {
		return hasCable;
	}

	public void setCable(final boolean hasCable) {
		this.hasCable = hasCable;
	}

	public boolean getGarage() {
		return hasGarage;
	}

	public void setGarage(final boolean hasGarage) {
		this.hasGarage = hasGarage;
	}

	public boolean getInternet() {
		return hasInternet;
	}

	public void setInternet(final boolean hasInternet) {
		this.hasInternet = hasInternet;
	}

	public String getEarliestMoveInDate() {
		return earliestMoveInDate;
	}

	public void setEarliestMoveInDate(final String earliestMoveInDate) {
		this.earliestMoveInDate = earliestMoveInDate;
	}

	public String getLatestMoveInDate() {
		return latestMoveInDate;
	}

	public void setLatestMoveInDate(final String latestMoveInDate) {
		this.latestMoveInDate = latestMoveInDate;
	}

	public String getEarliestMoveOutDate() {
		return earliestMoveOutDate;
	}

	public void setEarliestMoveOutDate(final String earliestMoveOutDate) {
		this.earliestMoveOutDate = earliestMoveOutDate;
	}

	public String getLatestMoveOutDate() {
		return latestMoveOutDate;
	}

	public void setLatestMoveOutDate(final String latestMoveOutDate) {
		this.latestMoveOutDate = latestMoveOutDate;
	}

	public boolean getStudioHelper() {
		return studioHelper;
	}

	public void setStudioHelper(final boolean helper) {
		this.studioHelper = helper;
	}

	public boolean getRoomHelper() {
		return roomHelper;
	}

	public void setRoomHelper(final boolean helper) {
		this.roomHelper = helper;
	}

	public boolean getHouseHelper() {
		return houseHelper;
	}

	public void setHouseHelper(final boolean helper) {
		this.houseHelper = helper;
	}

	public boolean getSaleHelper() {
		return saleHelper;
	}

	public void setSaleHelper(final boolean saleHelper) {
		this.saleHelper = saleHelper;
	}

	public boolean getRentHelper() {
		return rentHelper;
	}

	public void setRentHelper(final boolean rentHelper) {
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
