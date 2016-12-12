package ch.unibe.ese.team8.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * Adds or removes bookmarked ads from the user and updates the user accordingly.
 */
@Service
public class BookmarkService {

	@Autowired
	private UserDao userDao;

	/**
	 * This method adds or removes ads from the ArrayList.
	 *
	 * @param id, it's the current ads' id.
	 * @param bookmarked, tells the function the state of of the ad regarding bookmarks.
	 * @param bookmarkedAds, users current list of bookmarked ads.
	 * @param user, current user.
	 *
	 * @return returns an integer, <p>
	 * 								1 by default <p>
	 * 								2 undo the bookmark <p>
	 * 								3 bookmark it
	 */
	public int getBookmarkStatus(
			final Ad ad,
			final boolean bookmarked,
			final User user)
	{
		List<Ad> tempAdList = user.getBookmarkedAds();
		if(bookmarked)
		{
			tempAdList.remove(ad);
			updateUser(tempAdList, user);
			return 2;
		}

		if(!bookmarked)
		{
			tempAdList.add(ad);
			updateUser(tempAdList, user);
			return 3;
		}

		return 1;
	}

	/**
	 * Updates effectively the new List into DB.
	 *
	 * @param bookmakredAds, a List<Ad>.
	 * @param user
	 */
	private void updateUser(final List<Ad> bookmarkedAds, final User user)
	{
		user.setBookmarkedAds(bookmarkedAds);
		userDao.save(user);
	}
}