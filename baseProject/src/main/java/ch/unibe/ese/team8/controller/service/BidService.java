package ch.unibe.ese.team8.controller.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.MessageState;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.AdDao;
import ch.unibe.ese.team8.model.dao.MessageDao;

/**
 * Controls the bids, that are made by a user and what interactions should be made.
 */
@Service
public class BidService {

	@Autowired
	private AdDao adDao;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private SystemService systemService;

	/**
	 * This method handles the bid request.
	 *
	 * @param ad, the ad we want to bid on.
	 * @param bid, the current bid.
	 * @param user, the user who is bidding.
	 *
	 * @return returns an integer depending on the status<p>
	 *			0: the placed bid is lower than the max bid<p>
	 *			1: the bid was placed successfully<p>
	 *			2: the auction is actually over already, notify the corresponding peopl!<p>
	 *			3: the user is already the max bidder
	 */
	public int bid(final Ad ad, final int bid, final User user)
	{
		if (ad.getPrizePerMonth() >= bid && ad.getStartPrize() > bid)
		{
			return 0;
		} else if (ad.getAuctionEndDate().before(new Date()) || ad.getAuctionOver()) {
			ad.setAuctionOver(true);

			sendMessageToMaxBidder(ad);
			sendMessageToSeller(ad);

			adDao.save(ad);

			return 2;
		} else if (ad.getMaxBidder().getId() == user.getId()) { return 3; }
		else {

			// This could be improved, by introducing a new method, that does this!
			// NOTICE: Maybe we needed a utils class which would provide such things.
			Message message = new Message();

			message.setRecipient(ad.getMaxBidder());
			message.setSubject(systemService.getBidNotification());
			message.setText(systemService.getBidText(ad.getMaxBidder(), ad.getId(), ad.getAuctionEndDate(), bid));
			message.setState(MessageState.UNREAD);
			message.setSender(systemService.getAdmin());

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			message.setDateSent(calendar.getTime());

			messageDao.save(message);

			ad.setPrizePerMonth(bid);
			ad.setMaxBidder(user);
			adDao.save(ad);

			return 1;
		}
	}

	/**
	 * Checks if auction is expired and notify the seller and maxbidder.
	 */
	public void checkExpiredAuctions() {
		Iterable<Ad> allAuctions = adDao.findByAuction(true);
		for (Ad auction : allAuctions) {
			if (auction.getAuctionEndDate().before(new Date()))
			{
				if (!auction.getAuctionOver() && (auction.getMaxBidder().getId() != systemService.getAdmin().getId()))
				{
					auction.setAuctionOver(true);

					sendMessageToMaxBidder(auction);
					sendMessageToSeller(auction);

					adDao.save(auction);
				}
			}
		}
	}

	/**
	 * Sends a message to the seller, that the auction ended successfully.
	 *
	 * @param auction, an Ad.
	 */
	private void sendMessageToSeller(final Ad auction) {
		User maxBidder = auction.getMaxBidder();
		User seller = auction.getUser();
		long auctionId = auction.getId();

		Message message = new Message();
		Date now = new Date();

		message.setRecipient(seller);
		message.setSender(maxBidder);
		message.setSubject(systemService.getSaleNotification(seller, auctionId, maxBidder));
		message.setText(systemService.getSaleText(maxBidder, auctionId, seller));
		message.setState(MessageState.UNREAD);
		message.setDateSent(now);

		messageDao.save(message);
	}

	/**
	 * Sends a message to the buyer, that he won an auction.
	 *
	 * @param auction, an Ad.
	 */
	private void sendMessageToMaxBidder(final Ad auction) {
		User maxBidder = auction.getMaxBidder();
		User seller = auction.getUser();
		long auctionId = auction.getId();

		Message message = new Message();
		Date now = new Date();

		message.setRecipient(maxBidder);
		message.setSender(seller);
		message.setSubject(systemService.getBuyNotification());
		message.setText(systemService.getBuyText(maxBidder, auctionId, seller));
		message.setState(MessageState.UNREAD);
		message.setDateSent(now);

		messageDao.save(message);
	}
}