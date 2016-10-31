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

	public String getBidText(User maxBidder, long id, Date auctionEndDate) {
		StringBuffer message = new StringBuffer();

		message.append("Dear " + maxBidder.getFirstName() + " " + maxBidder.getLastName() + "</br>");
		message.append(
				"Somebody just bid more than youd did, on the following ad: <a href=\"http://localhost:8080/auction?id="
						+ id + "\">Find the Auction</a></br>");

		return message.toString();
	}

}
