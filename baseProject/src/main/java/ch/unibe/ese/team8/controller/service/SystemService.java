package ch.unibe.ese.team8.controller.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.MessageState;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.MessageDao;

/**
 * The SystemService holds a lot of prepared statements and texts, that are used
 * for the notification of auctions. We introduced this class, so that all the
 * logic is in one place.
 */
@Service
public class SystemService {

	@Autowired
	private UserService userService;

	@Autowired
	private AdService adService;

	@Autowired
	private MessageDao messageDao;

	public User getAdmin()
	{
		return userService.findUserById(1);
	}

	public String getBidNotification()
	{
		return "Somebody just bid more than you";
	}

	/**
	 * Forms the BidText by using predefined text strings and afterwards adds the
	 * missing pieces, which have been given as arguments to the string.
	 *
	 *
	 * @param maxBidder, a user.
	 * @param id, a long value.
	 * @param auctionEndDate
	 * @param prize
	 *
	 * @return the bid text.
	 */
	public String getBidText(
			final User maxBidder,
			final long id,
			final Date auctionEndDate,
			final int prize)
	{
		StringBuffer message = new StringBuffer();

		message.append("Dear " + maxBidder.getFirstName() + " " + maxBidder.getLastName() + "</br>");
		message.append(
				"Somebody just bid more than you did, on the following ad: <a href=\"http://localhost:8080/auction?id="
						+ id + "\">" + adService.getAdById(id).getTitle() + "</a></br>");
		message.append("</br>Note: The auction will end on the " + auctionEndDate.toString()
				+ " and the current price is " + prize);

		return message.toString();
	}

	public String getSaleNotification(final User user, final long id, final User buyer)
	{
		return "Your auction \"" + adService.getAdById(id).getTitle() + "\" ended";
	}

	public String getBuyNotification()
	{
		return "You won an auction!";
	}

	public String getBuyText(final User user, final long id, final User seller)
	{
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

	public String getSaleText(final User user, final long id, final User seller)
	{
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

	public String getInvoiceText(final User user)
	{
		StringBuffer message = new StringBuffer();

		message.append("Dear " + user.getFirstName() + " " + user.getLastName() + "</br>");
		message.append("Thank you for purchasing our premium packet, you now owe use some money!");
		return message.toString();
	}

	public void sendPremiumInvoice(final User user)
	{
		Message message = new Message();
		Date now = new Date();

		message.setRecipient(user);
		message.setSender(this.getAdmin());
		message.setSubject("Welcome in the premium flatbook club");
		message.setText(this.getInvoiceText(user));
		message.setState(MessageState.UNREAD);
		message.setDateSent(now);

		messageDao.save(message);
	}
}