package ch.unibe.ese.team8.controller;

import java.security.Principal;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team8.controller.pojos.forms.EditProfileForm;
import ch.unibe.ese.team8.controller.pojos.forms.GoogleLoginForm;
import ch.unibe.ese.team8.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team8.controller.pojos.forms.SearchForm;
import ch.unibe.ese.team8.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team8.controller.service.AdService;
import ch.unibe.ese.team8.controller.service.GoogleLoginService;
import ch.unibe.ese.team8.controller.service.GoogleService;
import ch.unibe.ese.team8.controller.service.SignupService;
import ch.unibe.ese.team8.controller.service.UserService;
import ch.unibe.ese.team8.controller.service.UserUpdateService;
import ch.unibe.ese.team8.controller.service.VisitService;
import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.Visit;

/**
 * Handles all requests concerning user accounts and profiles.
 */
@Controller
public class ProfileController {

	@Autowired
	private SignupService signupService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserUpdateService userUpdateService;

	@Autowired
	private VisitService visitService;

	@Autowired
	private AdService adService;
	
	@Autowired
	private GoogleService googleService;

	@Autowired
	private GoogleLoginService googleLoginService;
	
	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;

	/**
	 * Returns the login page by creating a new ModelAndView instance with
	 * <code>ModelAndView('login')</code>.
	 * 
	 * @return mode, a ModelAndView instance.
	 */
	@RequestMapping(value = "/login")
	public ModelAndView loginPage()
	{
		ModelAndView model = new ModelAndView("login");
		model.addObject("googleForm", new GoogleLoginForm());
		return model;
	}
	
	/**
	 * Handles the login and return from the google login form.
	 * 
	 * @param googleForm, a GoogleLoginForm.
	 * 
	 * @return model, the ModelAndView instance.
	 */
	@RequestMapping(value = "/google", method = RequestMethod.POST)
	public ModelAndView goolgeLogin(GoogleLoginForm googleForm)
	{
		ModelAndView model = new ModelAndView("index");
		if(!googleService.doesUserWithUsernameExist(googleForm.getEmail()))
		{
			googleService.saveFrom(googleForm);
		}
		googleLoginService.loginFrom(googleForm);
		model.addObject("newest", adService.getNewestAds(4));
		return model;
	}

	/**
	 * Returns the signup page.
	 * 
	 * @return mode, the ModelAndView instance.
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signupPage()
	{
		ModelAndView model = new ModelAndView("signup");
		model.addObject("signupForm", new SignupForm());
		return model;
	}

	/**
	 * Validates the signup form and on success persists the new user.
	 * 
	 * @param signupForm
	 * @param bindingResult
	 * 
	 * @return model, the ModelAndView instance.
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signupResultPage(
			@Valid final SignupForm signupForm,
			final BindingResult bindingResult)
	{
		ModelAndView model;
		if (!bindingResult.hasErrors())
		{
			signupService.saveFrom(signupForm);
			model = new ModelAndView("login");
			model.addObject("confirmationMessage", "Signup complete!");
		} else {
			model = new ModelAndView("signup");
			model.addObject("signupForm", signupForm);
		}
		return model;
	}

	/**
	 * Checks and returns whether a user with the given email already exists.
	 * 
	 * @param email
	 * 
	 * @return boolean, the result of
	 *                  <code>SignupService*doesUserWithUsernameExist(email)</code>
	 */
	@RequestMapping(value = "/signup/doesEmailExist", method = RequestMethod.POST)
	public @ResponseBody boolean doesEmailExist(@RequestParam final String email)
	{
		return signupService.doesUserWithUsernameExist(email);
	}

	/**
	 * Shows the edit profile page.
	 * 
	 * @param principal
	 * 
	 * @return mode, the ModelAndView instance.
	 */
	@RequestMapping(value = "/profile/editProfile", method = RequestMethod.GET)
	public ModelAndView editProfilePage(final Principal principal)
	{
		ModelAndView model = new ModelAndView("editProfile");
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		model.addObject("editProfileForm", new EditProfileForm());
		model.addObject("currentUser", user);
		return model;
	}

	/**
	 * Handles the request for editing the user profile.
	 * 
	 * @param editProfileForm,
	 * @param bindingResult,
	 * @param principal
	 * 
	 * @return mode, the ModelAndView instance.
	 */
	@RequestMapping(value = "/profile/editProfile", method = RequestMethod.POST)
	public ModelAndView editProfileResultPage(
			@Valid final EditProfileForm editProfileForm,
			final BindingResult bindingResult,
			final Principal principal)
	{
		ModelAndView model;
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (!bindingResult.hasErrors())
		{
			userUpdateService.updateFrom(editProfileForm);
			model = new ModelAndView("updatedProfile");
			model.addObject("message", "Your Profile has been updated!");
			model.addObject("currentUser", user);
			return model;
		} else {
			model = new ModelAndView("updatedProfile");
			model.addObject("message",
					"Something went wrong, please contact the WebAdmin if the problem persists!");
			return model;
		}
	}

	/**
	 * Displays the public profile of the user with the given id.
	 * 
	 * @param id, the id of the user.
	 * @param principal
	 * 
	 * @return model, the ModelAndView instance.
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView user(
			@RequestParam("id") final long id,
			final Principal principal)
	{
		ModelAndView model = new ModelAndView("user");
		User user = userService.findUserById(id);
		if (principal != null)
		{
			String username = principal.getName();
			User user2 = userService.findUserByUsername(username);
			long principalID = user2.getId();
			model.addObject("principalID", principalID);
		}
		model.addObject("user", user);
		model.addObject("messageForm", new MessageForm());
		return model;
	}

	/**
	 * Displays the schedule page of the currently logged in user.
	 * 
	 * @param principal
	 * 
	 * @return mode, the ModelAndView instance.
	 */
	@RequestMapping(value = "/profile/schedule", method = RequestMethod.GET)
	public ModelAndView schedule(final Principal principal)
	{
		ModelAndView model = new ModelAndView("schedule");
		User user = userService.findUserByUsername(principal.getName());

		// visits, i.e. when the user sees someone else's property
		Iterable<Visit> visits = visitService.getVisitsForUser(user);
		model.addObject("visits", visits);

		// presentations, i.e. when the user presents a property
		Iterable<Ad> usersAds = adService.getAdsByUser(user);
		ArrayList<Visit> usersPresentations = new ArrayList<Visit>();

		for (Ad ad : usersAds) {
			try {
				usersPresentations.addAll((ArrayList<Visit>) visitService
						.getVisitsByAd(ad));
			} catch (Exception e) {
			}
		}

		model.addObject("presentations", usersPresentations);
		return model;
	}

	/**
	 * Returns the visitors page for the visit with the given id
	 * 
	 * @param id, the id of the visit.
	 * 
	 * @return model, the ModelAndView instance.
	 */
	@RequestMapping(value = "/profile/visitors", method = RequestMethod.GET)
	public ModelAndView visitors(@RequestParam("visit") final long id)
	{
		ModelAndView model = new ModelAndView("visitors");
		Visit visit = visitService.getVisitById(id);
		Iterable<User> visitors = visit.getSearchers();

		model.addObject("visitors", visitors);

		Ad ad = visit.getAd();
		model.addObject("ad", ad);
		return model;
	}
}
