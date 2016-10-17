package ch.unibe.ese.team8.model.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.User;

public interface AdDao extends CrudRepository<Ad, Long> {

	/** this will be used if both rooms AND studios AND houses are searched */
	public Iterable<Ad> findByPrizePerMonthLessThan(int prize);

	public Iterable<Ad> findBySaleAndPrizePerMonthLessThan(boolean sale, int prize);

	/** this will be used if only rooms or studios are searched */

	public Iterable<Ad> findByCategoryInAndPrizePerMonthLessThan(ArrayList<String> s, int i);

	public Iterable<Ad> findByUser(User user);
}
