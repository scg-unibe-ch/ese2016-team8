package ch.unibe.ese.team8.test.testData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team8.model.Alert;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.AlertDao;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * This inserts some alert test data into the database.
 */
@Service
public class AlertTestDataSaver {

	@Autowired
	private AlertDao alertDao;

	@Autowired
	private UserDao userDao;


	@Transactional
	public void saveTestData() throws Exception {
		User ese = userDao.findByUsername("ese@unibe.ch");
		User jane = userDao.findByUsername("jane@doe.com");

		// 2 Alerts for the ese test-user.
		Alert alert = new Alert();
		TestDataUtils.polyfillAlert(alert,
				ese,
				"studio",
				"Bern",
				3000,
				1500,
				30);

		alertDao.save(alert);

		alert = new Alert();
		TestDataUtils.polyfillAlert(alert,
				ese,
				"room",
				"Zürich",
				8000,
				1000,
				25);

		alertDao.save(alert);

		// One alert for Jane Doe.
		alert = new Alert();
		TestDataUtils.polyfillAlert(alert,
				jane,
				"studio",
				"Luzern",
				6003,
				9000,
				22);

		alertDao.save(alert);
	}
}