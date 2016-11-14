package ch.unibe.ese.team8.bughunting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;

public class AuctionEndTimeTest {
	
	private Auction auction = new Auction();
	private AuctionService auctionService = new AuctionService();
	private PlaceAuctionForm placeAuctionForm = new PlaceAuctionForm();
	private User user = new User();	
	private List<String> filePaths = new ArrayList<String>();
	
	@Before
	public void setUp() {
		placeAuctionForm.setTitle("test");
		placeAuctionForm.setStreet("test");
		placeAuctionForm.setPrize(500);
		placeAuctionForm.setMoveInDate("12.24.2017");
		placeAuctionForm.setRoomDescription("test");
		placeAuctionForm.setSquareFootage(10);
		placeAuctionForm.setCity("3000 - Bern");
		placeAuctionForm.setAnimals(false);
		placeAuctionForm.setAuction(false);
		placeAuctionForm.setBalcony(false);
		placeAuctionForm.setBuyable(false);
		placeAuctionForm.setCable(false);
		placeAuctionForm.setCellar(false);
		placeAuctionForm.setFurnished(false);
		placeAuctionForm.setGarage(false);
		placeAuctionForm.setGarden(false);
		placeAuctionForm.setInternet(false);
		placeAuctionForm.setRoomType("room");
		placeAuctionForm.setSmokers(false);
		user.setId(42);
		user.setUsername("TestUser");
		user.setPassword("123456");
		user.setEmail("test@unibe.ch");
		user.setFirstName("Test");
		user.setLastName("User");
		user.setAccount("normal");
		user.setGender(Gender.MALE);
		user.setEnabled(true);
	}
	
	@Test
	public void testEndTime() {
		String testEndTime = "12:00, 12.11.2017";
		placeAuctionForm.setEndDate("12-11-2017");
		placeAuctionForm.setEndTime("12:00");
		auction = auctionService.saveFrom(placeAuctionForm, filePaths, user);
		assertEquals(testEndTime,auction.getEndTime());
	}
	
	@Test(expected = StringIndexOutOfBoundsException.class)
	public void testExceptionEndTime() {
		placeAuctionForm.setEndDate("12-11-2017");
		placeAuctionForm.setEndTime("12h");
		auction = auctionService.saveFrom(placeAuctionForm, filePaths, user);
	}
	
	@Test
	public void testWrongEndTime() {
		String testEndTime = "09:30, 12.11.2017";
		placeAuctionForm.setEndDate("12-11-2017");
		placeAuctionForm.setEndTime("9:30");
		auction = auctionService.saveFrom(placeAuctionForm, filePaths, user);
		assertNotEquals(testEndTime,auction.getEndTime());
	}
	

}
