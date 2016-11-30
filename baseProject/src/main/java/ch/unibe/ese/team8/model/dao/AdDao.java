package ch.unibe.ese.team8.model.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.User;

public interface AdDao extends CrudRepository<Ad, Long> {

	public Iterable<Ad> findByUser(User user);

	public Iterable<Ad> findByAuction(boolean auction);
	
	/** this will be used if we search for ads on sale AND for rent */
	public Iterable<Ad> findByCategoryInAndPrizePerMonthLessThanOrderByPremiumDesc(ArrayList<String> categories, int i);
	
	public Iterable<Ad> findByCategoryInAndAuctionOverAndPrizePerMonthLessThanOrderByPremiumDesc(ArrayList<String> categories, boolean auction, int i);

	/** this will be used if we search for ads EITHER on sale OR for rent */
	public Iterable<Ad> findByCategoryInAndSaleAndPrizePerMonthLessThanOrderByPremiumDesc(ArrayList<String> categories, boolean sale,
			int i);
	
	public Iterable<Ad> findByCategoryInAndSaleAndAuctionOverAndPrizePerMonthLessThanOrderByPremiumDesc(ArrayList<String> categories, boolean sale,
			boolean auctionOver,int i);
	
	public Iterable<Ad> findByAuctionOver(boolean auctioOver);

}
