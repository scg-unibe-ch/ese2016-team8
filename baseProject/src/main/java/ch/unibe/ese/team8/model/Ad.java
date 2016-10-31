package ch.unibe.ese.team8.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/** Describes an advertisement that users can place and search for. */
@Entity
public class Ad {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String street;

	@Column(nullable = false)
	private int zipcode;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date moveInDate;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date moveOutDate;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date auctionEndDate;

	@Column(nullable = false)
	private int prizePerMonth;

	@Column(nullable = false)
	private int squareFootage;

	@Column(nullable = false)
	@Lob
	private String roomDescription;

	@Column(nullable = false)
	@Lob
	private String preferences;

	@Column(nullable = true)
	private String roommates;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> registeredRoommates;

	@Column(nullable = false)
	private boolean allowsSmokers;

	@Column(nullable = false)
	private boolean allowsAnimals;

	@Column(nullable = false)
	private boolean hasGarden;

	@Column(nullable = false)
	private boolean hasBalcony;

	@Column(nullable = false)
	private boolean hasCellar;

	@Column(nullable = false)
	private boolean isFurnished;

	@Column(nullable = false)
	private boolean hasCable;

	@Column(nullable = false)
	private boolean hasGarage;

	@Column(nullable = false)
	private boolean hasInternet;

	@Column(nullable = false)
	private String category;

	@Column(nullable = false)
	private boolean sale;

	@Column(nullable = false)
	private boolean auction;

	@Column(nullable = false)
	private int startPrize;

	// @Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date auctionDuration;

	@ManyToOne(optional = true)
	private User maxBidder;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AdPicture> pictures;

	@ManyToOne(optional = false)
	private User user;

	@OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Visit> visits;

	@Column(nullable = false)
	private boolean premium;

	@Column(nullable = false)
	private boolean auctionOver;

	public void setAuctionOver(boolean end){
		this.auctionOver = end;
	}

	public boolean getAuctionOver(){
		return auctionOver;
	}
	public Date getAuctionEndDate(){
		return auctionEndDate;
	}

	public void setAuctionEndDate(final Date auctionEndDate){
		this.auctionEndDate= auctionEndDate;

	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean getSale() {
		return sale;
	}

	public void setSale(final boolean sale) {
		this.sale = sale;
	}

	public boolean getAuction() {
		return auction;
	}

	public void setAuction(boolean auction) {
		this.auction = auction;
	}

	public int getStartPrize() {
		return startPrize;
	}

	public void setStartPrize(int startPrize) {
		this.startPrize = startPrize;
	}

	public Date getAuctionDuration() {
		return auctionDuration;
	}

	public void setAuctionDuration(Date auctionDuration) {
		this.auctionDuration = auctionDuration;
	}

	public User getMaxBidder() {
		return maxBidder;
	}

	public void setMaxBidder(User maxBidder) {
		this.maxBidder = maxBidder;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public boolean getPremium() {
		return premium;
	}

	public boolean getSmokers() {
		return allowsSmokers;
	}

	public void setSmokers(final boolean allowsSmokers) {
		this.allowsSmokers = allowsSmokers;
	}

	public boolean getAnimals() {
		return allowsAnimals;
	}

	public void setAnimals(final boolean allowsAnimals) {
		this.allowsAnimals = allowsAnimals;
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

	public void setFurnished(final boolean furnished) {
		this.isFurnished = furnished;
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

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(final int zipcode) {
		this.zipcode = zipcode;
	}

	public Date getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(final Date moveInDate) {
		this.moveInDate = moveInDate;
	}

	public Date getMoveOutDate() {
		return moveOutDate;
	}

	public void setMoveOutDate(final Date moveOutDate) {
		this.moveOutDate = moveOutDate;
	}

	public int getPrizePerMonth() {
		return prizePerMonth;
	}

	public void setPrizePerMonth(final int prizePerMonth) {
		this.prizePerMonth = prizePerMonth;
	}

	public int getSquareFootage() {
		return squareFootage;
	}

	public void setSquareFootage(final int squareFootage) {
		this.squareFootage = squareFootage;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(final String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(final String preferences) {
		this.preferences = preferences;
	}

	public String getRoommates() {
		return roommates;
	}

	public void setRoommates(final String roommates) {
		this.roommates = roommates;
	}

	public List<AdPicture> getPictures() {
		return pictures;
	}

	public void setPictures(final List<AdPicture> pictures) {
		this.pictures = pictures;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public Date getDate(final boolean hasDate) {
		if (hasDate)
			return getMoveInDate();
		else
			return getMoveOutDate();
	}

	public List<User> getRegisteredRoommates() {
		return registeredRoommates;
	}

	public void setRegisteredRoommates(final List<User> registeredRoommates) {
		this.registeredRoommates = registeredRoommates;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(final List<Visit> visits) {
		this.visits = visits;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	// equals method is defined to check for id only
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ad other = (Ad) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
