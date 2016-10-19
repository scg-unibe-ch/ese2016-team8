package ch.unibe.ese.team8.test.testData;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.unibe.ese.team8.controller.service.AdService;
import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.AdPicture;
import ch.unibe.ese.team8.model.Alert;
import ch.unibe.ese.team8.model.Gender;
import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.MessageState;
import ch.unibe.ese.team8.model.Rating;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.UserPicture;
import ch.unibe.ese.team8.model.UserRole;
import ch.unibe.ese.team8.model.Visit;
import ch.unibe.ese.team8.model.VisitEnquiry;
import ch.unibe.ese.team8.model.VisitEnquiryState;

/**
 * class which offers static polyfill methods in order to simplify
 * adding properties to objects
 */
public class TestDataUtils
{

	/**
	 *
	 * @param ad
	 * @param zipcode
	 * @param moveInDate
	 * @param creationDate
	 * @param moveOutDate
	 * @param prizePerMonth
	 * @param squareFootage
	 * @param category
	 * @param allowsSmokers
	 * @param allowsAnimals
	 * @param studioDescription
	 * @param roomPreferences
	 * @param roommates
	 * @param user
	 * @param title
	 * @param street
	 * @param city
	 * @param hasGarden
	 * @param hasBalcony
	 * @param hasCellar
	 * @param isFurnished
	 * @param hasCable
	 * @param hasGarage
	 * @param hasInternet
	 */
	public static Ad polyfillAd(
			final Ad ad,
			final int zipcode,
			final Date moveInDate,
			final Date creationDate,
			final Date moveOutDate,
			final int prizePerMonth,
			final int squareFootage,
			final String category,
			final boolean allowsSmokers,
			final boolean allowsAnimals,
			final String studioDescription,
			final String roomPreferences,
			final String roommates,
			final User user,
			final List<User> registeredRoommates,
			final String title,
			final String street,
			final String city,
			final boolean hasGarden,
			final boolean hasBalcony,
			final boolean hasCellar,
			final boolean isFurnished,
			final boolean hasCable,
			final boolean hasGarage,
			final boolean hasInternet,
			final boolean sale,
			final List<AdPicture> pictures)
	{
		ad.setZipcode( zipcode );
		ad.setMoveInDate( moveInDate );
		ad.setCreationDate( creationDate );
		ad.setMoveOutDate( moveOutDate );
		ad.setPrizePerMonth( prizePerMonth );
		ad.setSquareFootage( squareFootage );
		ad.setCategory( category );
		ad.setSmokers( allowsSmokers );
		ad.setAnimals( allowsAnimals );
		ad.setRoomDescription(studioDescription);
		ad.setPreferences(roomPreferences);
		ad.setRoommates( roommates );
		ad.setUser( user );
		ad.setRegisteredRoommates( registeredRoommates );
		ad.setTitle( title );
		ad.setStreet( street );
		ad.setCity( city );
		ad.setGarden( hasGarden );
		ad.setBalcony( hasBalcony );
		ad.setCellar( hasCellar );
		ad.setFurnished( isFurnished );
		ad.setCable( hasCable );
		ad.setGarage( hasGarage );
		ad.setInternet( hasInternet );
		ad.setSale( sale );
		ad.setPictures( pictures );

		return ad;
	}

	/**
	 *
	 * @param alert
	 * @param user
	 * @param category
	 * @param city
	 * @param zipcode
	 * @param price
	 * @param radius
	 * @return
	 */
	public static Alert polyfillAlert(
			final Alert alert,
			final User user,
			final String category,
			final String city,
			final int zipcode,
			final int price,
			final int radius)
	{
		alert.setUser( user );
		alert.setCategory( category );
		alert.setCity( city );
		alert.setZipcode( zipcode );
		alert.setPrice( price );
		alert.setRadius( radius );
		return alert;
	}

	/**
	 * streams the long array and adds correspondending adServices to the bookmakredAds linkedlist
	 * @param bookmarkedAds
	 * @param adService
	 * @param longArray
	 * @return
	 */
	public static LinkedList<Ad> polyfillBookmarkedAdsById(final LinkedList<Ad> bookmarkedAds,
			final AdService adService, final long[] longArray)
	{
		Arrays.stream(longArray).forEach(l -> bookmarkedAds.add(adService.getAdById(l)));
		return bookmarkedAds;
	}

	/**
	 *
	 * @param message
	 * @param subject
	 * @param text
	 * @param sender
	 * @param recipient
	 * @param state
	 * @param dateSent
	 * @return
	 */
	public static Message polyfillMessage(final Message message, final String subject, final String text,
			final User sender, final User recipient, final MessageState state, final Date dateSent)
	{
		message.setSubject( subject );
		message.setText( text );
		message.setSender( sender );
		message.setRecipient( recipient );
		message.setState( state );
		message.setDateSent( dateSent );
		return message;
	}

	/**
	 *
	 * @param rating
	 * @param rater
	 * @param ratee
	 * @param ratingValue
	 * @return
	 */
	public static Rating polyfillRating(final Rating rating, final User rater, final User ratee, final int ratingValue)
	{
		rating.setRater( rater );
		rating.setRatee( ratee );
		rating.setRating( ratingValue );
		return rating;
	}

	/**
	 *
	 * @param email
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param picPath
	 * @param gender
	 * @return
	 */
	public static User polyfillUser(final User user, final String email, final String password, final String firstName,
			final String lastName, final String picPath, final Gender gender, final String aboutMe) {
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEnabled(true);
		user.setGender(gender);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		UserPicture picture = new UserPicture();
		picture.setUser(user);
		picture.setFilePath(picPath);
		user.setPicture(picture);
		role.setRole("ROLE_USER");
		role.setUser(user);
		userRoles.add(role);
		user.setUserRoles(userRoles);
		user.setAboutMe(aboutMe);
		return user;
	}

	/**
	 *
	 * @param enquiry
	 * @param dateSent
	 * @param sender
	 * @param state
	 * @param visit
	 * @return
	 */
	public static VisitEnquiry polyfillEnquiry(
			final VisitEnquiry enquiry,
			final Date dateSent,
			final User sender,
			final VisitEnquiryState state,
			final Visit visit)
	{
		enquiry.setDateSent( dateSent );
		enquiry.setSender( sender );
		enquiry.setState( state );
		enquiry.setVisit( visit );
		return enquiry;
	}
}
