package ch.unibe.ese.team8.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.unibe.ese.team8.controller.pojos.PictureUploader;
import ch.unibe.ese.team8.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team8.controller.service.AdService;
import ch.unibe.ese.team8.controller.service.AlertService;
import ch.unibe.ese.team8.controller.service.EditAdService;
import ch.unibe.ese.team8.controller.service.UserService;
import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.PictureMeta;
import ch.unibe.ese.team8.model.User;

/**
 * This controller handles all requests concerning editing ads.
 */
@Controller
public class EditAdController {

	private final static String IMAGE_DIRECTORY = PlaceAdController.IMAGE_DIRECTORY;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private AdService adService;

	@Autowired
	private EditAdService editAdService;

	@Autowired
	private UserService userService;

	@Autowired
	private AlertService alertService;

	private PictureUploader pictureUploader;

	private ObjectMapper objectMapper;

	/**
	 * Serves the page that allows the user to edit the ad with the given id.
	 *
	 * @param id, the id of the ad.
	 * @param principal
	 *
	 * @return model, a ModelAndView where the new placeAdForm has been added.
	 */
	@RequestMapping(value = "/profile/editAd", method = RequestMethod.GET)
	public ModelAndView editAdPage(@RequestParam final long id, final Principal principal)
	{
		ModelAndView model = new ModelAndView("editAd");
		Ad ad = adService.getAdById(id);
		model.addObject("ad", ad);

		PlaceAdForm form = editAdService.fillForm(ad);

		model.addObject("placeAdForm", form);

		String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
		if (pictureUploader == null) {
			pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
		}

		return model;
	}

	/**
	 * Processes the edit ad form and displays the result page to the user.
	 *
	 * @param placeAdForm
	 * @param result, the BiddingResult instance.
	 * @param principal
	 * @param redirectAttributes
	 * @param adId
	 *
	 * @return model, the new ModelAndView instance.
	 */
	@RequestMapping(value = "/profile/editAd", method = RequestMethod.POST)
	public ModelAndView editAdPageWithForm(
			 			@Valid final PlaceAdForm placeAdForm,
			 			final BindingResult result,
			 			final Principal principal,
			 			final RedirectAttributes redirectAttributes,
			 			@RequestParam final long adId){
		ModelAndView model = new ModelAndView("placeAd");
		if (!result.hasErrors()) {
			String username = principal.getName();
			User user = userService.findUserByUsername(username);

			String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
			if (pictureUploader == null) {
				pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
			}
			List<String> fileNames = pictureUploader.getFileNames();
			Ad ad = editAdService.saveFrom(placeAdForm, fileNames, user, adId);

			// Triggers all alerts that match the placed ad.
			alertService.triggerAlerts(ad);

			// Reset the picture uploader.
			this.pictureUploader = null;

			if (placeAdForm.getAuction()) {
				model = new ModelAndView("redirect:/auction?id=" + ad.getId());
				redirectAttributes.addFlashAttribute("confirmationMessage",
						"Ad edited successfully. You can take a look at it below.");
			} else {
				model = new ModelAndView("redirect:/ad?id=" + ad.getId());
				redirectAttributes.addFlashAttribute("confirmationMessage",
						"Ad edited successfully. You can take a look at it below.");
			}
		} else {
			model = new ModelAndView("redirect:/profile/editAd?id=" + adId);
		}
		return model;
	}

	/**
	 * Deletes the ad picture with the given id from the list of pictures from
	 * the ad, but not from the server.
	 *
	 * @param adId
	 * @param pictureId
	 */
	@RequestMapping(value = "/profile/editAd/deletePictureFromAd", method = RequestMethod.POST)
	public @ResponseBody void deletePictureFromAd(
			 			@RequestParam final long adId,
			 			@RequestParam final long pictureId)
	{
		editAdService.deletePictureFromAd(adId, pictureId);
	}

	/**
	 * Gets the descriptions for the pictures that were uploaded with the
	 * current picture uploader.
	 *
	 * @return List<PictureMeta>, a list of picture descriptions or null if no pictures were uploaded.
	 */
	@RequestMapping(value = "/profile/editAd/getUploadedPictures", method = RequestMethod.POST)
	public @ResponseBody List<PictureMeta> getUploadedPictures() {
		if (pictureUploader == null) {
			return null;
		}
		return pictureUploader.getUploadedPictureMetas();
	}

	/**
	 * Uploads the pictures that are attached as multipart files to the request.
	 * The JSON representation, that is returned, is generated manually because
	 * the jQuery Fileupload plugin requires this special format.
	 *
	 * @param request, a MultipartHttpServletRequest.
	 *
	 * @return String, a JSON representation of the uploaded files.
	 */
	@RequestMapping(value = "/profile/editAd/uploadPictures", method = RequestMethod.POST)
	public @ResponseBody String uploadPictures(final MultipartHttpServletRequest request) {
		List<MultipartFile> pictures = new LinkedList<>();
		Iterator<String> iter = request.getFileNames();

		while (iter.hasNext()) {
			pictures.add(request.getFile(iter.next()));
		}

		List<PictureMeta> uploadedPicturesMeta = pictureUploader.upload(pictures);

		objectMapper = new ObjectMapper();
		String jsonResponse = "{\"files\": ";
		try {
			jsonResponse += objectMapper.writeValueAsString(uploadedPicturesMeta);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		jsonResponse += "}";
		return jsonResponse;
	}

	/**
	 * Deletes the uploaded picture at the given relative url (relative to the
	 * webapp folder).
	 *
	 * @param url, the String respresentation of the url of the picture, which is
	 * to be deleted.
	 */
	@RequestMapping(value = "/profile/editAd/deletePicture", method = RequestMethod.POST)
	public @ResponseBody void deleteUploadedPicture(@RequestParam final String url) {
		if (pictureUploader != null) {
			String realPath = servletContext.getRealPath(url);
			pictureUploader.deletePicture(url, realPath);
		}
	}

	/**
	 * Deletes the roommate with the given id.
	 *
	 * @param userId, the id of the user to delete
	 * @param adId, the id of the ad to delete the user from
	 */
	@RequestMapping(value = "/profile/editAd/deleteRoommate", method = RequestMethod.POST)
	public @ResponseBody void deleteRoommate(
				@RequestParam final long userId,
				@RequestParam final long adId)
	{
		editAdService.deleteRoommate(userId, adId);
	}
}