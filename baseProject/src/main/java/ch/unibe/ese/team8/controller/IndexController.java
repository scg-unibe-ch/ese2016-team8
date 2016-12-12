package ch.unibe.ese.team8.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team8.controller.service.AdService;
import ch.unibe.ese.team8.controller.service.BidService;
import ch.unibe.ese.team8.model.Ad;

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
		
		Iterable<Ad> adList = adService.getNewestAds(4);
		List<Ad> ads = new ArrayList<Ad>();
		for (Ad ad : adList)
			ads.add(ad);
		Collections.sort(ads, new Comparator<Ad>() {
			@Override
			public int compare(final Ad ad1, final Ad ad2) {
				if(ad1.getUser().getPremium() && !ad2.getUser().getPremium()){
					return -1;
				}else if(!ad1.getUser().getPremium() && ad2.getUser().getPremium()){
					return 1;
				}else{
					return ad2.getCreationDate().compareTo(ad1.getCreationDate());
				}	
			}
		});
			
		model.addObject("newest", ads);
		
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