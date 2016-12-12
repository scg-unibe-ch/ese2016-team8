package ch.unibe.ese.team8.test.testData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team8.model.Rating;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.RatingDao;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * This inserts some alert test data into the database.
 */
@Service
public class RatingTestDataSaver {

	@Autowired
	RatingDao ratingDao;

	@Autowired
	UserDao userDao;

	@Transactional
	public void saveTestData() throws Exception {

		User ese = userDao.findByUsername("ese@unibe.ch");
		User jane = userDao.findByUsername("jane@doe.com");
		User oprah = userDao.findByUsername("oprah@winfrey.com");
		User berner = userDao.findByUsername("user@bern.com");

		// Ese's ratings.
		Rating rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				ese,
				berner,
				3);

		ratingDao.save(rating);

		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				ese,
				oprah,
				4);

		ratingDao.save(rating);

		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				ese,
				jane,
				5);

		ratingDao.save(rating);

		// Berner Bär doesn't rate anyone.
		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				berner,
				ese,
				0);

		ratingDao.save(rating);

		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				berner,
				oprah,
				0);

		ratingDao.save(rating);

		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				berner,
				jane,
				0);

		ratingDao.save(rating);

		// Oprah loves everyone.
		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				oprah,
				jane,
				5);

		ratingDao.save(rating);

		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				oprah,
				berner,
				5);

		ratingDao.save(rating);

		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				oprah,
				ese,
				5);

		ratingDao.save(rating);

		// Jane hasn't invited many people.
		rating = new Rating();
		TestDataUtils.polyfillRating(rating,
				jane,
				berner,
				2);

		ratingDao.save(rating);
	}
}