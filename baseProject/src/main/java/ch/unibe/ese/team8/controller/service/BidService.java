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
 * Adds or removes bookmarked ads from the user and updates the user accordingly
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
	 * This method adds or removes ads from the ArrayList.
	 * 
	 * @param id
	 *            it's the current ads' id
	 * @param bid
	 *            the current bid
	 * @param user
	 *            current user
	 * 
	 * @return returns an integer 3 bookmark it 2 undo the bookmark
	 * 
	 */
	public int bid(Ad ad, int bid, User user) {
		if (ad.getPrizePerMonth() >= bid && ad.getStartPrize() > bid) {
			return 0;
		} else {
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

	public void checkExpiredAuctions() {
		Iterable<Ad> allAuctions = adDao.findByAuction(true);
		for (Ad auction : allAuctions) {
			if (auction.getAuctionEndDate().before(new Date())) {
				if (!auction.getAuctionOver()) {
					auction.setAuctionOver(true);

					sendMessageToMaxBidder(auction);
					sendMessageToSeller(auction);

					adDao.save(auction);
				}
			}
		}
	}

	private void sendMessageToSeller(Ad auction) {
		User maxBidder = auction.getMaxBidder();
		User seller = auction.getUser();
		long auctionId = auction.getId();
		
		Message message = new Message();
		
		message.setRecipient(seller);
		message.setSender(maxBidder);
		message.setSubject(systemService.getSaleNotification(seller, auctionId, maxBidder));
		message.setText(systemService.getSaleText(maxBidder, auctionId, seller));
		
				
		messageDao.save(message);
		
	}

	private void sendMessageToMaxBidder(Ad auction) {
		// TODO Auto-generated method stub
		
	}

}
