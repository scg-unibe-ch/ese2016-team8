package ch.unibe.ese.team8.bughunting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.UserDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class AdServiceTest {

	@Autowired
	private AdService adService;

	@Autowired
	private UserDao userDao;

	/**
	 * In order to test the saved ad, I need to get it back from the DB again, so these
	 * two methods need to be tested together, normally we want to test things isolated of
	 * course. Testing just the returned ad from saveFrom() wouldn't answer the question
	 * whether the ad has been saved correctly to the db.
	 * @throws ParseException
	 */
	@Test
	public void saveFromAndGetById() throws ParseException {
		//Preparation
		PlaceAdForm placeAdForm = new PlaceAdForm();
		placeAdForm.setCity("3018 - Bern");
		placeAdForm.setPreferences("Test preferences");
		placeAdForm.setRoomDescription("Test Room description");
		placeAdForm.setPrize(600);
		placeAdForm.setSquareFootage(50);
		placeAdForm.setTitle("title");
		placeAdForm.setStreet("Hauptstrasse 13");
		placeAdForm.setRoomType("Studio");
		placeAdForm.setMoveInDate("27-02-2015");
		placeAdForm.setMoveOutDate("27-04-2015");

		placeAdForm.setSmokers(true);
		placeAdForm.setAnimals(false);
		placeAdForm.setGarden(true);
		placeAdForm.setBalcony(false);
		placeAdForm.setCellar(true);
		placeAdForm.setFurnished(false);
		placeAdForm.setCable(false);
		placeAdForm.setGarage(true);
		placeAdForm.setInternet(false);

		ArrayList<String> filePaths = new ArrayList<>();
		filePaths.add("/img/test/ad1_1.jpg");

		User hans = createUser("hans@kanns.ch", "password", "Hans", "Kanns",
				Gender.MALE, "Premium");
		hans.setAboutMe("Hansi Hinterseer");
		userDao.save(hans);

		adService.saveFrom(placeAdForm, filePaths, hans);

		Ad ad = new Ad();
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad = iterator.next();
		}

		//Testing
		assertTrue(ad.getSmokers());
		assertFalse(ad.getAnimals());
		assertEquals("Bern", ad.getCity());
		assertEquals(3018, ad.getZipcode());
		assertEquals("Test preferences", ad.getPreferences());
		assertEquals("Test Room description", ad.getRoomDescription());
		assertEquals(600, ad.getPrize());
		assertEquals(50, ad.getSquareFootage());
		assertEquals("title", ad.getTitle());
		assertEquals("Hauptstrasse 13", ad.getStreet());
		assertEquals("Studio", ad.getRoomType());

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date result =  df.parse("2015-02-27");

		assertEquals(0, result.compareTo(ad.getMoveInDate()));
	}

	/**
	 * The method is called issue3 as it is referencing it as is from the numeric
	 * description of the issues of team8.
	 * TODO: change name.
	 *
	 * This test demonstrates that **NO EXCEPTION** is thrown when setting the move-out
	 * date earlier than the move-in date which equals to a logical inconsitency.
	 *
	 * NOTICE: As it is not known which Exception should be thrown when above inconsitency
	 * occurs, we expect the mother of all Exceptions.
	 * @throws ParseException
	 *
	 */
	@Test(expected = Exception.class)
	public void issue3()
	{
		//Preparation
		PlaceAdForm placeAdForm = new PlaceAdForm();
		placeAdForm.setCity("3018 - Bern");
		placeAdForm.setPreferences("Test preferences");
		placeAdForm.setRoomDescription("Test Room description");
		placeAdForm.setPrize(600);
		placeAdForm.setSquareFootage(50);
		placeAdForm.setTitle("title");
		placeAdForm.setStreet("Hauptstrasse 13");
		placeAdForm.setRoomType("Studio");
		placeAdForm.setMoveInDate("27-02-2015");
		placeAdForm.setMoveOutDate("27-01-2015"); // Remark move-out date is month before move-in date.

		placeAdForm.setSmokers(true);
		placeAdForm.setAnimals(false);
		placeAdForm.setGarden(true);
		placeAdForm.setBalcony(false);
		placeAdForm.setCellar(true);
		placeAdForm.setFurnished(false);
		placeAdForm.setCable(false);
		placeAdForm.setGarage(true);
		placeAdForm.setInternet(false);

		ArrayList<String> filePaths = new ArrayList<>();
		filePaths.add("/img/test/ad1_1.jpg");

		User hans2 = createUser("hans@kannsnicht.ch", "password", "Hans", "Kanns",
				Gender.MALE, "Premium");
		hans2.setAboutMe("Hansi Hinterseer");
		userDao.save(hans2);

		adService.saveFrom(placeAdForm, filePaths, hans2);

		Ad ad = new Ad();
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();

		while (iterator.hasNext()) { ad = iterator.next(); }

		//Testing
		assertTrue(ad.getSmokers());
		assertFalse(ad.getAnimals());
		assertEquals("Bern", ad.getCity());
		assertEquals(3018, ad.getZipcode());
		assertEquals("Test preferences", ad.getPreferences());
		assertEquals("Test Room description", ad.getRoomDescription());
		assertEquals(600, ad.getPrize());
		assertEquals(50, ad.getSquareFootage());
		assertEquals("title", ad.getTitle());
		assertEquals("Hauptstrasse 13", ad.getStreet());
		assertEquals("Studio", ad.getRoomType());
		String moveInDate = "2015-02-27";
		String moveOutDate = "2015-01-27";

		assertEquals(0, moveInDate.compareTo(ad.getMoveInDate().toString()));
		assertEquals(0, moveOutDate.compareTo(ad.getMoveOutDate().toString()));
	}

	private User createUser(final String email, final String password, final String firstName,
			final String lastName, final Gender gender, final String account) {
		User user = new User();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEnabled(true);
		user.setGender(gender);
		user.setAccount(account);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole("ROLE_USER");
		role.setUser(user);
		userRoles.add(role);
		user.setUserRoles(userRoles);
		return user;
	}

}
