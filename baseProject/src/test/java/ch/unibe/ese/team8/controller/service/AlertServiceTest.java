package ch.unibe.ese.team8.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.Alert;
import ch.unibe.ese.team8.model.Gender;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.UserRole;
import ch.unibe.ese.team8.model.dao.AdDao;
import ch.unibe.ese.team8.model.dao.AlertDao;
import ch.unibe.ese.team8.model.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class AlertServiceTest {

	@Autowired
	AdDao adDao;

	@Autowired
	UserDao userDao;

	@Autowired
	AlertDao alertDao;

	@Autowired
	AlertService alertService;

	@Test
	public void createAlerts() {
		ArrayList<Alert> alertList = new ArrayList<Alert>();

		// Create user Adolf Ogi.
		User adolfOgi2 = createUser("adolf@ogi2.ch", "password", "Adolf", "Ogi",
				Gender.MALE);
		adolfOgi2.setAboutMe("Wallis rocks");
		userDao.save(adolfOgi2);

		// Create 2 alerts for Adolf Ogi.
		Alert alert = new Alert();
		alert.setUser(adolfOgi2);
		alert.setCategory("studio");
		alert.setCity("Bern");
		alert.setZipcode(3001);
		alert.setPrice(1500);
		alert.setRadius(100);
		alertDao.save(alert);

		alert = new Alert();
		alert.setUser(adolfOgi2);
		alert.setCategory("studio");
		alert.setCity("Bern");
		alert.setZipcode(3002);
		alert.setPrice(1000);
		alert.setRadius(5);
		alertDao.save(alert);

		// Copy alerts to a list.
		Iterable<Alert> alerts = alertService.getAlertsByUser(adolfOgi2);
		for(Alert returnedAlert: alerts)
			alertList.add(returnedAlert);

		// Begin the actual testing.
		assertEquals(2, alertList.size());
		assertEquals(adolfOgi2, alertList.get(0).getUser());
		assertEquals("Bern", alertList.get(1).getCity());
		assertTrue(alertList.get(0).getRadius() > alertList.get(1).getRadius());
	}

	@Test
	public void mismatchChecks() {
		ArrayList<Alert> alertList = new ArrayList<Alert>();

		User thomyF2 = createUser("thomy@f2.ch", "password", "Thomy", "F",
				Gender.MALE);
		thomyF2.setAboutMe("Supreme hustler");
		userDao.save(thomyF2);

		// Create 2 alerts for Thomy F.
		Alert alert = new Alert();
		alert.setUser(thomyF2);
		alert.setCategory("studio");
		alert.setCity("Bern");
		alert.setZipcode(3003);
		alert.setPrice(1500);
		alert.setRadius(100);
		alertDao.save(alert);

		alert = new Alert();
		alert.setUser(thomyF2);
		alert.setCategory("room");
		alert.setCity("Bern");
		alert.setZipcode(3002);
		alert.setPrice(1000);
		alert.setRadius(5);
		alertDao.save(alert);

		Iterable<Alert> alerts = alertService.getAlertsByUser(userDao.findByUsername("thomy@f2.ch"));
		for(Alert returnedAlert: alerts)
			alertList.add(returnedAlert);

		// Save an ad.
		Date date = new Date();
		Ad oltenResidence2= new Ad();
		oltenResidence2.setZipcode(4600);
		oltenResidence2.setMoveInDate(date);
		oltenResidence2.setCreationDate(date);
		oltenResidence2.setPrizePerMonth(1200);
		oltenResidence2.setSquareFootage(42);
		oltenResidence2.setCategory("room");
		oltenResidence2.setSmokers(true);
		oltenResidence2.setAnimals(false);
		oltenResidence2.setRoomDescription("blah");
		oltenResidence2.setPreferences("blah");
		oltenResidence2.setRoommates("None");
		oltenResidence2.setUser(thomyF2);
		oltenResidence2.setTitle("Olten Residence");
		oltenResidence2.setStreet("Florastr. 100");
		oltenResidence2.setCity("Olten");
		oltenResidence2.setGarden(false);
		oltenResidence2.setBalcony(false);
		oltenResidence2.setCellar(false);
		oltenResidence2.setFurnished(false);
		oltenResidence2.setCable(false);
		oltenResidence2.setGarage(false);
		oltenResidence2.setInternet(false);
		adDao.save(oltenResidence2);

		assertFalse(alertService.radiusMismatch(oltenResidence2, alertList.get(0)));
		assertTrue(alertService.radiusMismatch(oltenResidence2, alertList.get(1)));
		assertTrue(alertService.typeMismatch(oltenResidence2, alertList.get(0)));
		assertFalse(alertService.typeMismatch(oltenResidence2, alertList.get(1)));
	}

	// Lean user creating method.
	User createUser(final String email, final String password, final String firstName,
			final String lastName, final Gender gender) {
		User user = new User();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEnabled(true);
		user.setGender(gender);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole("ROLE_USER");
		role.setUser(user);
		userRoles.add(role);
		user.setUserRoles(userRoles);
		return user;
	}
}
