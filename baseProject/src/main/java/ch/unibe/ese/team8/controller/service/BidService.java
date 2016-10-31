package ch.unibe.ese.team8.controller.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.MessageState;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.AdDao;
import ch.unibe.ese.team8.model.dao.MessageDao;

/** Adds or removes bookmarked ads from the user and updates the user accordingly */
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
	 *          it's the current ads' id
	 * @param bid
	 *          the current bid
	 * @param user
	 *          current user
	 *          
	 * @return returns an integer 3 bookmark it
	 *                            2 undo the bookmark
	 * 
	 */
	public int bid(Ad ad, int bid, User user) {
		if(ad.getPrizePerMonth() >= bid && ad.getStartPrize() > bid){
			return 0;
		}else{
			Message message = new Message();
			
			message.setRecipient(ad.getMaxBidder());
			message.setSubject(systemService.getBidNotification());
			message.setText(systemService.getBidText(ad.getMaxBidder(), ad.getId(), ad.getAuctionEndDate()));
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

}
