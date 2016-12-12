package ch.unibe.ese.team8.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team8.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team8.controller.service.AdService;
import ch.unibe.ese.team8.controller.service.BidService;
import ch.unibe.ese.team8.controller.service.BookmarkService;
import ch.unibe.ese.team8.controller.service.MessageService;
import ch.unibe.ese.team8.controller.service.UserService;
import ch.unibe.ese.team8.controller.service.VisitService;
import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.User;

/**
 * This controller handles all requests concerning displaying ads and
 * bookmarking them.
 */
@Controller
public class AdController {

	@Autowired
	private AdService adService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookmarkService bookmarkService;

	@Autowired
	private BidService bidService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private VisitService visitService;

	/**
	 * Gets the ad description page for the ad with the given id.
	 *
	 * @param id, the id of the ad, retrieved by <code>AdService#getAdById(id)</code>
	 * @param principal
	 *
	 * @return model, the modified ModelAndView instance.
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public ModelAndView ad(
			@RequestParam("id") final long id,
			final Principal principal)
	{
		ModelAndView model = new ModelAndView("adDescription");
		Ad ad = adService.getAdById(id);
		model.addObject("shownAd", ad);
		model.addObject("messageForm", new MessageForm());

		String loggedInUserEmail = (principal == null) ? "" : principal
				.getName();
		model.addObject("loggedInUserEmail", loggedInUserEmail);

		model.addObject("visits", visitService.getVisitsByAd(ad));

		return model;
	}

	/**
	 *  Gets the auction description for the ad with the given id.
	 *
	 *  @param id, the id of the auctionable ad.
	 *  @param principal.
	 *
	 *  @return model, the modified ModelAndView instance.
	 */
	@RequestMapping(value = "/auction", method = RequestMethod.GET)
	public ModelAndView auction(
			@RequestParam("id") final long id,
			final Principal principal)
	{
		ModelAndView model = new ModelAndView("auction");
		Ad ad = adService.getAdById(id);
		model.addObject("shownAd", ad);
		model.addObject("messageForm", new MessageForm());

		String loggedInUserEmail = (principal == null) ? "" : principal
				.getName();
		model.addObject("loggedInUserEmail", loggedInUserEmail);

		model.addObject("visits", visitService.getVisitsByAd(ad));

		return model;
	}

	/**
	 * Gets the ad description page for the ad with the given id and also
	 * validates and persists the message passed as post data.
	 *
	 * @param id, the id of the Ad where the message has been 'produced'.
	 * @param messageForm
	 * @param bindingResult.
	 *
	 * @return model, the modified ModelAndView instance.
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public ModelAndView messageSent(
			@RequestParam("id") final long id,
			@Valid final MessageForm messageForm,
			final BindingResult bindingResult)
	{
		ModelAndView model = new ModelAndView("adDescription");
		Ad ad = adService.getAdById(id);
		model.addObject("shownAd", ad);
		model.addObject("messageForm", new MessageForm());

		if (!bindingResult.hasErrors()) {
			messageService.saveFrom(messageForm);
		}
		return model;
	}

	/**
	 * Checks if the adID passed as post parameter is already inside user's
	 * List bookmarkedAds. In case it is present, true is returned changing
	 * the "Bookmark Ad" button to "Bookmarked". If it is not present it is
	 * added to the List bookmarkedAds.
	 *
	 * @param id, the id of the ad where the bookmarked has been placed on.
	 * @param screening
	 * @param bookmarked
	 * @param principal
	 *
	 * @return 0 and 1 for errors; <p>
	 *         3 to update the button to bookmarked <p>
	 *         3 and 2 for bookmarking or undo bookmarking respectively <p>
	 *         4 for removing button completly (because its the users ad)
	 */
	@RequestMapping(value = "/bookmark", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public int isBookmarked(
			@RequestParam("id") final long id,
			@RequestParam("screening") final boolean screening,
			@RequestParam("bookmarked") final boolean bookmarked,
			final Principal principal)
	{
		// Should never happen since no bookmark button when not logged in.
		if (principal == null) {
			return 0;
		}
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (user == null) {
			// That should not happen...
			return 1;
		}
		List<Ad> bookmarkedAdsIterable = user.getBookmarkedAds();
		if (screening) {
			for (Ad ownAdIterable : adService.getAdsByUser(user)) {
				if (ownAdIterable.getId() == id) {
					return 4;
				}
			}
			for (Ad adIterable : bookmarkedAdsIterable) {
				if (adIterable.getId() == id) {
					return 3;
				}
			}
			return 2;
		}

		Ad ad = adService.getAdById(id);

		return bookmarkService.getBookmarkStatus(ad, bookmarked, user);
	}

	/**
	 * Passes a bid for an auction on to the bidService with
	 * <code>BidService#bid(Adservice ad,int bid, User user)</code>
	 *
	 * @param id, the id of the ad
	 * @param screening
	 * @param currentBid
	 * @param principal
	 *
	 * @return 0 for errors, <code>BidService#bid(ad, bid, user)</code> else.
	 */
	@RequestMapping(value = "/auctionBid", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public int bidOnAuction(
			@RequestParam("id") final long id,
			@RequestParam("screening") final boolean screening,
			@RequestParam("bid") final int currentBid,
			final Principal principal)
	{

		// Should never happen since no bookmark button when not logged in.
		if (principal == null) {
			return 0;
		}
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (user == null) {
			// That should not happen.
			return 0;
		}

		int bid = currentBid;
		Ad ad = adService.getAdById(id);

		return bidService.bid(ad, bid, user);
	}

	/**
	 * Fetches information about bookmarked rooms and own ads and attaches this
	 * information to the myRooms page in order to be displayed.
	 *
	 * @param principal
	 *
	 * @return a new ModelAndView('home') instance if the principal was null, else the
	 * modified ModelAndView instance 'model'.
	 */
	@RequestMapping(value = "/profile/myRooms", method = RequestMethod.GET)
	public ModelAndView myRooms(final Principal principal)
	{
		ModelAndView model;
		User user;
		if (principal != null) {
			model = new ModelAndView("myRooms");
			String username = principal.getName();
			user = userService.findUserByUsername(username);

			Iterable<Ad> ownAds = adService.getAdsByUser(user);

			model.addObject("bookmarkedAdvertisements", user.getBookmarkedAds());
			model.addObject("ownAdvertisements", ownAds);
			return model;
		} else {
			model = new ModelAndView("home");
		}
		return model;
	}
}