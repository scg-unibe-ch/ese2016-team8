package ch.unibe.ese.team8.model.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.User;

public interface AdDao extends CrudRepository<Ad, Long> {

	/** this will be used if both rooms AND studios AND houses are searched */
	public Iterable<Ad> findByPrizePerMonthLessThanOrderByPremiumDesc(int prize);

	public Iterable<Ad> findBySaleAndPrizePerMonthLessThanOrderByPremiumDesc(boolean sale, int prize);

	/** this will be used if only rooms or studios are searched */

	public Iterable<Ad> findByUser(User user);

	public Iterable<Ad> findByCategoryInAndPrizePerMonthLessThanOrderByPremiumDesc(ArrayList<String> categories, int i);

	public Iterable<Ad> findByCategoryInAndSaleAndPrizePerMonthLessThanOrderByPremiumDesc(ArrayList<String> categories, boolean sale,
			int i);
}
