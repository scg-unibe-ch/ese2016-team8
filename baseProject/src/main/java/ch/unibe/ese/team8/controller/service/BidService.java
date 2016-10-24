package ch.unibe.ese.team8.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.AdDao;

/** Adds or removes bookmarked ads from the user and updates the user accordingly */
@Service
public class BidService {

	
//	@Autowired
//	private UserDao userDao;
	@Autowired
	private AdDao adDao;
	
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
			ad.setPrizePerMonth(bid);
			ad.setMaxBidder(user);
			adDao.save(ad);
			return 1;
		}
	}
}
