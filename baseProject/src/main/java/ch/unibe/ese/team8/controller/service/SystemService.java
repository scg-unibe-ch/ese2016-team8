package ch.unibe.ese.team8.controller.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team8.model.User;

@Service
public class SystemService {

	@Autowired
	private UserService userService;

	@Autowired
	private AdService adService;

	public User getAdmin() {
		return userService.findUserById(1);
	}

	public String getBidNotification() {
		return "Somebody just bid more than you";
	}

	public String getBidText(User maxBidder, long id, Date auctionEndDate, int prize) {
		StringBuffer message = new StringBuffer();

		message.append("Dear " + maxBidder.getFirstName() + " " + maxBidder.getLastName() + "</br>");
		message.append(
				"Somebody just bid more than you did, on the following ad: <a href=\"http://localhost:8080/auction?id="
						+ id + "\">"+ adService.getAdById(id).getTitle()  +"</a></br>");
		message.append("</br>Note: The auction will end on the " + auctionEndDate.toString() +" and the current price is " + prize);

		return message.toString();
	}

	public String getSaleNotification(User user, long id, User buyer) {
//		StringBuffer message = new StringBuffer();
//
//		message.append("Dear " + user.getFirstName() + " " + user.getLastName() + "</br>");
//		message.append(
//				"Congratulations, your auction ended successfully! The see your ad: <a href=\"http://localhost:8080/auction?id="
//						+ id + "\">click here</a></br>");
//		message.append(
//				"</br> Please get in contact with the buyer, as soon as possible. You find his contact information below!");
//		message.append("</br> " + buyer.getFirstName() + " " + buyer.getLastName() + " " + buyer.getEmail());

		return "Your auction \"" + adService.getAdById(id).getTitle() + "\" ended";
	}
	
	public String getBuyNotification(){
		return "You won an auction!";
	}

	public String getBuyText(User user, long id, User seller){
		StringBuffer message = new StringBuffer();

		message.append("Dear " + user.getFirstName() + " " + user.getLastName() + "</br>");
		message.append(
				"Congratulations, your made the highest bid on an auction! The see your ad: <a href=\"http://localhost:8080/auction?id="
						+ id + "\">click here</a></br>");
		message.append(
				"</br> Please get in contact with the seller, as soon as possible. You find his contact information below!");
		message.append("</br> " + seller.getFirstName() + " " + seller.getLastName() + " " + seller.getEmail());

		return message.toString();
	}
	
	public String getSaleText(User user, long id, User seller) {
		StringBuffer message = new StringBuffer();

		message.append("Dear " + seller.getFirstName() + " " + seller.getLastName() + "</br>");
		message.append(
				"Congratulations, your auction ended successfully! The see your ad: <a href=\"http://localhost:8080/auction?id="
						+ id + "\">click here</a></br>");
		message.append(
				"</br> Please get in contact with the buyer, as soon as possible. You find his contact information below!");
		message.append("</br> " + user.getFirstName() + " " + user.getLastName() + " " + user.getEmail());

		return message.toString();
	}

}
