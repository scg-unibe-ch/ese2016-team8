package ch.unibe.ese.team8.controller;

import java.io.IOException;
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
import ch.unibe.ese.team8.log.Logger;
import ch.unibe.ese.team8.log.LoggerForm;
import ch.unibe.ese.team8.log.RequestState;
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

	/** Gets the ad description page for the ad with the given id.
	 * @throws IOException */
	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public ModelAndView ad(@RequestParam("id") final long id, final Principal principal) throws IOException {

		LoggerForm logForm = new LoggerForm();
		logForm.createHeader(RequestState.CALLING);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				"Principal: " + principal.getName());
		logForm.createResult();
		Logger lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();

		ModelAndView model = new ModelAndView("adDescription");
		Ad ad = adService.getAdById(id);
		model.addObject("shownAd", ad);
		model.addObject("messageForm", new MessageForm());

		String loggedInUserEmail = (principal == null) ? "" : principal
				.getName();
		model.addObject("loggedInUserEmail", loggedInUserEmail);

		model.addObject("visits", visitService.getVisitsByAd(ad));

		logForm.createHeader(RequestState.SUCCESS);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				"Principal: " + principal.getName(),
				"ModelAndView: " + model.getViewName(),
				"Ad id: " + String.valueOf(ad.getId()));
		logForm.createResult();
		lg = new Logger();
		lg.logToFile( logForm );
		return model;
	}

	/** Gets the auction description for the ad with the given id
	 * @throws IOException */
	@RequestMapping(value = "/auction", method = RequestMethod.GET)
	public ModelAndView auction(@RequestParam("id") final long id, final Principal principal) throws IOException {

		LoggerForm logForm = new LoggerForm();
		logForm.createHeader(RequestState.CALLING);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				"Principal: " + principal.getName());
		logForm.createResult();
		Logger lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();

		ModelAndView model = new ModelAndView("auction");
		Ad ad = adService.getAdById(id);
		model.addObject("shownAd", ad);
		model.addObject("messageForm", new MessageForm());

		String loggedInUserEmail = (principal == null) ? "" : principal
				.getName();
		model.addObject("loggedInUserEmail", loggedInUserEmail);

		model.addObject("visits", visitService.getVisitsByAd(ad));

		logForm.createHeader(RequestState.SUCCESS);
		logForm.createBody("Class: " + getClass().getName().substring(0,  getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				"Principal: " + principal.getName(),
				"ModelAndView: " + model.getViewName(),
				"Ad id: " + ad.getId());
		logForm.createResult();
		lg.logToFile( logForm );
		logForm.clear();
		return model;
	}


	/**
	 * Gets the ad description page for the ad with the given id and also
	 * validates and persists the message passed as post data.
	 * @throws IOException
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public ModelAndView messageSent(@RequestParam("id") final long id,
			@Valid final MessageForm messageForm, final BindingResult bindingResult) throws IOException {

		LoggerForm logForm = new LoggerForm();
		logForm.createHeader(RequestState.CALLING);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				messageForm.toString());
		logForm.createResult();
		Logger lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();

		ModelAndView model = new ModelAndView("adDescription");
		Ad ad = adService.getAdById(id);
		model.addObject("shownAd", ad);
		model.addObject("messageForm", new MessageForm());

		if (!bindingResult.hasErrors()) {
			messageService.saveFrom(messageForm);
		}

		logForm = new LoggerForm();
		logForm.createHeader(RequestState.SUCCESS);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				messageForm.toString(),
				"ModelAndView: " + model.getView(),
				"Ad id: " + ad.getId());
		logForm.createResult();
		lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();
		return model;
	}

	/**
	 * Checks if the adID passed as post parameter is already inside user's
	 * List bookmarkedAds. In case it is present, true is returned changing
	 * the "Bookmark Ad" button to "Bookmarked". If it is not present it is
	 * added to the List bookmarkedAds.
	 *
	 * @return 0 and 1 for errors; 3 to update the button to bookmarked 3 and 2
	 *         for bookmarking or undo bookmarking respectively 4 for removing
	 *         button completly (because its the users ad)
	 * @throws IOException
	 */
	@RequestMapping(value = "/bookmark", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public int isBookmarked(@RequestParam("id") final long id,
			@RequestParam("screening") final boolean screening,
			@RequestParam("bookmarked") final boolean bookmarked, final Principal principal) throws IOException {
		// should never happen since no bookmark button when not logged in
		LoggerForm logForm = new LoggerForm();
		logForm.createHeader(RequestState.CALLING);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				"Screening: " + screening,
				"Bookmarked: " + bookmarked,
				"Principal: " + principal.getName());
		logForm.createResult();
		Logger lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();

		if (principal == null) {
			return 0;
		}
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (user == null) {
			// that should not happen...
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

		logForm = new LoggerForm();
		logForm.createHeader(RequestState.SUCCESS);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				"UserName: " + username,
				"User id: " + user.getId(),
				"Ad id: " + ad.getId());
		logForm.createResult();
		lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();

		return bookmarkService.getBookmarkStatus(ad, bookmarked, user);
	}

	@RequestMapping(value = "/auctionBid", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public int bidOnAuction(@RequestParam("id") final long id,
			@RequestParam("screening") final boolean screening, @RequestParam("bid") final int currentBid, final Principal principal) throws IOException {
		// should never happen since no bookmark button when not logged in

		LoggerForm logForm = new LoggerForm();
		logForm.createHeader(RequestState.CALLING);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				"Screening: " + screening,
				"CurrentBid: " + currentBid,
				"Principal: " + principal.getName());
		logForm.createResult();
		Logger lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();

		if (principal == null) {
			return 0;
		}
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (user == null) {
			// that should not happen...
			return 0;
		}

		if (screening) {

		}

		int bid = currentBid;
		Ad ad = adService.getAdById(id);

		logForm = new LoggerForm();
		logForm.createHeader(RequestState.CALLING);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Id: " + id,
				"CurrentBid: " + bid,
				"Username: " + username,
				"User id: " + user.getId(),
				"Ad id: " + ad.getId());
		logForm.createResult();
		lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();

		return bidService.bid(ad, bid, user);
	}

	/**
	 * Fetches information about bookmarked rooms and own ads and attaches this
	 * information to the myRooms page in order to be displayed.
	 * @throws IOException
	 */
	@RequestMapping(value = "/profile/myRooms", method = RequestMethod.GET)
	public ModelAndView myRooms(final Principal principal) throws IOException {

		LoggerForm logForm = new LoggerForm();
		logForm.createHeader(RequestState.CALLING);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Principal: " + principal.getName());

		logForm.createResult();
		Logger lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();

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

		logForm = new LoggerForm();
		logForm.createHeader(RequestState.CALLING);
		logForm.createBody("Class: " + getClass().getName().substring(0, getClass().getName().indexOf("$")),
				"Method: " + Thread.currentThread().getStackTrace()[1],
				"Username: " + principal.getName());
		logForm.createResult();
		lg = new Logger();
		lg.logToFile( logForm );
		logForm.clear();


		return model;
	}

}