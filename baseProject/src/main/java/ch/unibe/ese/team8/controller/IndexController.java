package ch.unibe.ese.team8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team8.controller.service.AdService;
import ch.unibe.ese.team8.controller.service.BidService;

/**
 * This controller handles request concerning the home page and several other
 * simple pages.
 */
@Controller
public class IndexController {

	@Autowired
	private AdService adService;
	
	@Autowired
	private BidService bidService;

	/**
	 * Displays the home page.
	 * 
	 * @return model, the modified ModelAndView instance.
	 */
	@RequestMapping(value = "/")
	public ModelAndView index()
	{
		ModelAndView model = new ModelAndView("index");
		bidService.checkExpiredAuctions();
		model.addObject("newest", adService.getNewestAds(4));
		
		return model;
	}

	/**
	 * Displays the about us page by returning a new ModelAndView('about') instance.
	 * 
	 * @return ModelAndView('about'), returns by default a new ModelAndView instance.
	 */
	@RequestMapping(value = "/about")
	public ModelAndView about()
	{
		return new ModelAndView("about");
	}

	/**
	 * Displays the disclaimer page by creating/ returning a new ModelAndView('disclaimer')
	 * instance.
	 * 
	 * @return ModelAndView('disclaimer'), by default.
	 */
	@RequestMapping(value = "/disclaimer")
	public ModelAndView disclaimer()
	{
		return new ModelAndView("disclaimer");
	}
}