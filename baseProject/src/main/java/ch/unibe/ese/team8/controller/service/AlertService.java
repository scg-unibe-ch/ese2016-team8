package ch.unibe.ese.team8.controller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team8.controller.pojos.forms.AlertForm;
import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.Alert;
import ch.unibe.ese.team8.model.Location;
import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.MessageState;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.AlertDao;
import ch.unibe.ese.team8.model.dao.MessageDao;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * Provides and handles persistence operations for adding, editing and deleting
 * alerts.
 */
@Service
public class AlertService {

	@Autowired
	UserDao userDao;

	@Autowired
	AlertDao alertDao;

	@Autowired
	MessageDao messageDao;

	@Autowired
	private GeoDataService geoDataService;

	private static final long HOUR = 3600 * 1000;

	/**
	 * Persists a new alert with the data from the alert form to the database.
	 *
	 * @param alertForm, the form to take the data from.
	 * @param user, the user to associate the new alert to.
	 */
	@Transactional
	public void saveFrom(final AlertForm alertForm, final User user)
	{
		Alert alert = new Alert();

		String zip = alertForm.getCity().substring(0, 4);
		alert.setZipcode(Integer.parseInt(zip));
		alert.setCity(alertForm.getCity().substring(7));

		alert.setPrice(alertForm.getPrice());
		alert.setRadius(alertForm.getRadius());
		alert.setCategory(alertForm.getCategory());
		alert.setUser(user);
		alertDao.save(alert);
	}

	/**
	 * Returns all alerts that belong to the given user.
	 */
	@Transactional
	public Iterable<Alert> getAlertsByUser(final User user) {
		return alertDao.findByUser(user);
	}

	/**
	 * Deletes the alert with the given id.
	 */
	@Transactional
	public void deleteAlert(final Long id) {
		alertDao.delete(id);
	}

	/**
	 * Triggers all alerts that match the given ad. For every user, only one
	 * message is sent.
	 *
	 * @param ad
	 */
	@Transactional
	public void triggerAlerts(final Ad ad) {
		int adPrice = ad.getPrizePerMonth();
		Iterable<Alert> alerts = alertDao.findByPriceGreaterThan(adPrice - 1);

		// loop through all ads with matching city and price range, throw out
		// mismatches
		Iterator<Alert> alertIterator = alerts.iterator();
		while (alertIterator.hasNext()) {
			Alert alert = alertIterator.next();
			if (typeMismatchWith(ad, alert) || radiusMismatchWith(ad, alert) || ad.getUser().equals(alert.getUser()))
				alertIterator.remove();
		}

		// send only one message per user, no matter how many alerts were
		// triggered
		List<User> users = new ArrayList<User>();
		for (Alert alert : alerts) {
			User user = alert.getUser();
			if (!users.contains(user)) {
				users.add(user);
			}
		}

		// send messages to all users with matching alerts
		for (User user : users) {

			Date now;

			if (user.getPremium()) {
				now = new Date();
			} else {
				now = new Date();
				now.setTime(now.getTime() + 2 * HOUR);
			}
			Message message = new Message();
			message.setSubject("It's a match!");
			message.setText(getAlertText(ad));
			message.setSender(userDao.findByUsername("System"));
			message.setRecipient(user);
			message.setState(MessageState.UNREAD);
			message.setDateSent(now);
			messageDao.save(message);
		}
	}

	/**
	 * Returns the text for an alert message with the properties of the given
	 * ad.
	 *
	 * @param ad
	 *
	 * @return alertText
	 */
	private String getAlertText(final Ad ad) {
		StringBuffer message = new StringBuffer();

		message.append("Dear user,<br>good news. A new ad matching one of your alerts has been "
				+ "entered into our system. You can visit it here:<br><br>" + "<a class=\"link\"");

		if (ad.getAuction()) {
			message.append("href=/auction?id=");
		} else {
			message.append("href=/ad?id=");
		}
		message.append(
				ad.getId() + ">" + ad.getTitle() + "</a><br><br>" + "Good luck and enjoy,<br>" + "Your FlatFindr crew");

		return message.toString();
	}

	/**
	 * Checks if an ad is conforming to the criteria in an alert.
	 *
	 * @param ad
	 * @param alert
	 *
	 * @return a boolean
	 */
	private boolean typeMismatchWith(final Ad ad, final Alert alert) {
		boolean mismatch = false;

		if (!alert.getCategory().contains(ad.getCategory()))
			mismatch = true;
		return mismatch;
	}

	/**
	 * Checks whether an ad is for a place too far away from the alert.
	 *
	 * @param ad, the ad to compare to the alert
	 * @param alert, the alert to compare to the ad
	 *
	 * @return true in case the alert does not match the ad (the ad is too far
	 *         away), false otherwise
	 */
	private boolean radiusMismatchWith(final Ad ad, final Alert alert)
	{
		final int earthRadiusKm = 6380;
		Location adLocation = geoDataService.getLocationsByCity(ad.getCity()).get(0);
		Location alertLocation = geoDataService.getLocationsByCity(alert.getCity()).get(0);

		double radSinLat = Math.sin(Math.toRadians(adLocation.getLatitude()));
		double radCosLat = Math.cos(Math.toRadians(adLocation.getLatitude()));
		double radLong = Math.toRadians(adLocation.getLongitude());
		double radLongitude = Math.toRadians(alertLocation.getLongitude());
		double radLatitude = Math.toRadians(alertLocation.getLatitude());
		double distance = Math.acos(radSinLat * Math.sin(radLatitude)
				+ radCosLat * Math.cos(radLatitude) * Math.cos(radLong - radLongitude)) * earthRadiusKm;
		return (distance > alert.getRadius());
	}

	/**
	 * for testing
	 */
	public boolean radiusMismatch(final Ad ad, final Alert alert)
	{
		return radiusMismatchWith(ad, alert);
	}

	/**
	 * for testing
	 */
	public boolean typeMismatch(final Ad ad, final Alert alert)
	{
		return typeMismatchWith(ad, alert);
	}
}
