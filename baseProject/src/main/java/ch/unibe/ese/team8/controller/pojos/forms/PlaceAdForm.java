package ch.unibe.ese.team8.controller.pojos.forms;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/** This form is used when a user wants to place a new ad. */
public class PlaceAdForm {

	@NotBlank(message = "Required")
	private String title;

	@NotBlank(message = "Required")
	private String street;

	@Pattern(regexp = "^[0-9]{4} - [-\\w\\s\\u00C0-\\u00FF]*", message = "Please pick a city from the list")
	private String city;

	@NotBlank(message = "Required")
	private String moveInDate;

	private String moveOutDate;

	private String auctionEndDate;

	@Min(value = 1, message = "Has to be equal to 1 or more")
	private int prize;

	private int startPrize;

	@Min(value = 1, message = "Has to be equal to 1 or more")
	private int squareFootage;

	@NotBlank(message = "Required")
	private String roomDescription;

	private String preferences;

	// optional free text description
	private String roommates;

	// First user are added as strings, then transformed
	// to Users and added to the DB in through adService
	private List<String> registeredRoommateEmails;

	// optional for input
	private String roomFriends;

	private String category;
	private boolean sale;
	private boolean auction;

	private boolean smokers;
	private boolean animals;
	private boolean garden;
	private boolean balcony;
	private boolean cellar;
	private boolean furnished;
	private boolean cable;
	private boolean garage;
	private boolean internet;

	private List<String> visits;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}

	public int getStartPrize() {
		return startPrize;
	}

	public void setStartPrize(int startPrize) {
		this.startPrize = startPrize;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public int getSquareFootage() {
		return squareFootage;
	}

	public void setSquareFootage(int squareFootage) {
		this.squareFootage = squareFootage;
	}

	public String getRoommates() {
		return roommates;
	}

	public void setRoommates(String roommates) {
		this.roommates = roommates;
	}

	public boolean isSmokers() {
		return smokers;
	}

	public void setSmokers(boolean allowsSmokers) {
		this.smokers = allowsSmokers;
	}

	public boolean isAnimals() {
		return animals;
	}

	public void setAnimals(boolean allowsAnimals) {
		this.animals = allowsAnimals;
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

	public boolean isFurnished() {
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

	public String getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(String moveInDate) {
		this.moveInDate = moveInDate;
	}

	public String getAuctionEndDate(){
		return auctionEndDate;
	}
	public void setAuctionEndDate(String auctionEndDate){
		this.auctionEndDate = auctionEndDate;
	}
	public String getMoveOutDate() {
		return moveOutDate;
	}

	public void setMoveOutDate(String moveOutDate) {
		this.moveOutDate = moveOutDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getRoomFriends() {
		return roomFriends;
	}

	public void setRoomFriends(String roomFriends) {
		this.roomFriends = roomFriends;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSale(String category) {
		this.category = category;
	}

	public boolean getSale() {
		return sale;
	}

	public void setSale(boolean sale) {
		this.sale = sale;
	}

	public boolean getAuction() {
		return auction;
	}

	public void setAuction(boolean auction) {
		this.auction = auction;
	}

	public List<String> getRegisteredRoommateEmails() {
		return registeredRoommateEmails;
	}

	public void setRegisteredRoommateEmails(List<String> registeredRoommateEmails) {
		this.registeredRoommateEmails = registeredRoommateEmails;
	}

	public List<String> getVisits() {
		return visits;
	}

	public void setVisits(List<String> visits) {
		this.visits = visits;
	}
}
