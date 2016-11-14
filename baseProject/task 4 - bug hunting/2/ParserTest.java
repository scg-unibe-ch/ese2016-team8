package ch.unibe.ese.team1.test.testData;

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
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.UserDao;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/config/springMVC.xml",
    "file:src/main/webapp/WEB-INF/config/springData.xml",
    "file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class ParserTest {

  @Autowired
  private AdService adService;

  @Autowired
  private AdDao adDao;

  @Autowired UserDao userDao;

	  @Test
	  public void saveFromAndGetByUser() throws ParseException {
	    //Preparation
	    PlaceAdForm placeAdForm = new PlaceAdForm();
	    placeAdForm.setCity("2504 - Biel;Bienne");
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
	    Iterable<Ad> ads = adService.getAdsByUser(hans);
	    Iterator<Ad> iterator = ads.iterator();

	    ad = iterator.next();

	    assertEquals("2504 - Biel;Bienne", ad.getZipcode()+" - "+ad.getCity());


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
