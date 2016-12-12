package ch.unibe.ese.team8.test.testData;

import static ch.unibe.ese.team8.test.testData.TestDataUtils.polyfillBookmarkedAdsById;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team8.controller.service.AdService;
import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * This inserts some bookmark test data into the database.
 */
@Service
public class BookmarkTestDataSaver{

	@Autowired
	private UserDao userDao;

	@Autowired
	private AdService adService;

	@Transactional
	public void saveTestData() throws Exception {
		User ese = userDao.findByUsername("ese@unibe.ch");
		User jane = userDao.findByUsername("jane@doe.com");
		User bernerBaer = userDao.findByUsername("user@bern.com");
		User oprah = userDao.findByUsername("oprah@winfrey.com");

		// 5 bookmarks for Ese test-user.
		LinkedList<Ad> bookmarkedAds = new LinkedList<>();
		long[] longArray1 = {1,3,5,7,8};

		polyfillBookmarkedAdsById(bookmarkedAds, adService, longArray1);

		ese.setBookmarkedAds(bookmarkedAds);
		userDao.save(ese);

		// 4 bookmarks for Jane Doe.
		bookmarkedAds = new LinkedList<>();

		long[] longArray2 = {6,9,10,11};

		polyfillBookmarkedAdsById(bookmarkedAds, adService, longArray2);

		jane.setBookmarkedAds(bookmarkedAds);
		userDao.save(jane);

		// 5 bookmarks for user berner bear.
		bookmarkedAds = new LinkedList<>();

		long[] longArray3 = {2,4,6,8,12};

		polyfillBookmarkedAdsById(bookmarkedAds, adService, longArray3);

		bernerBaer.setBookmarkedAds(bookmarkedAds);
		userDao.save(bernerBaer);

		// 4 bookmarks for Oprah.
		bookmarkedAds = new LinkedList<>();

		long[] longArray4 = {2,3,6,12};

		polyfillBookmarkedAdsById(bookmarkedAds, adService, longArray4);

		oprah.setBookmarkedAds(bookmarkedAds);
		userDao.save(oprah);
	}
}