package ch.unibe.ese.team8.test.testData;

import static ch.unibe.ese.team8.test.testData.TestDataUtils.polyfillMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.MessageState;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.MessageDao;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * This inserts some messages test data into the database.
 */
@Service
public class MessageTestDataSaver {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageDao messageDao;

	private User bernerBaer;
	private User testerMuster;
	private User jane;
	private User oprah;

	@Transactional
	public void saveTestData() throws Exception {
		// Load users.
		bernerBaer = userDao.findByUsername("user@bern.com");
		testerMuster = userDao.findByUsername("ese@unibe.ch");
		jane = userDao.findByUsername("jane@doe.com");
		oprah = userDao.findByUsername("oprah@winfrey.com");

		Message message;
		DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

		// Messages for testerMuster.
		message = new Message();
		polyfillMessage(message,
				"Cool ad",
				"Hello Mr. Wayne\n\n" + getDummyText1(),
				jane,
				testerMuster,
				MessageState.UNREAD,
				dateFormat.parse("12:02 24.02.2014"));

		messageDao.save(message);

		message = new Message();
		polyfillMessage(message,
				"I agree",
				"Hey again Mr. Wayne\n\n" + getDummyText2(),
				bernerBaer,
				testerMuster,
				MessageState.UNREAD,
				dateFormat.parse("12:30 24.02.2014"));

		messageDao.save(message);

		message = new Message();
		polyfillMessage(message,
				"Check this out",
				"Hello Mr. Bär\n " + getDummyText3(),
				oprah,
				testerMuster,
				MessageState.READ,
				dateFormat.parse("11:30 24.02.2014"));

		messageDao.save(message);

		// Messages for JaneDoe.
		message = new Message();
		polyfillMessage(message,
				"I agree",
				"Hey Jane\n\n" + getDummyText2(),
				bernerBaer,
				jane,
				MessageState.UNREAD,
				dateFormat.parse("12:30 24.02.2014"));

		messageDao.save(message);

		message = new Message();
		polyfillMessage(message,
				"Check this out",
				"Whats up Jane?\n\n" + getDummyText3(),
				oprah,
				jane,
				MessageState.READ,
				dateFormat.parse("11:30 24.02.2014"));

		messageDao.save(message);

		// Messages for Berner Bär.
		message = new Message();
		polyfillMessage(message,
				"Awesome Ad",
				"Hey Huggy bear\n\n" + getDummyText1(),
				jane,
				bernerBaer,
				MessageState.UNREAD,
				dateFormat.parse("12:30 24.02.2014"));

		messageDao.save(message);

		message = new Message();
		polyfillMessage(message,
				"Insane Ad",
				"Whats up Mr. bear?\n\n" + getDummyText3(),
				oprah,
				bernerBaer,
				MessageState.READ,
				dateFormat.parse("11:30 24.02.2014"));

		messageDao.save(message);

		// Messages for Oprah.
		message = new Message();
		polyfillMessage(message,
				"Best Ad ever",
				"Hey Oprah\n\n" + getDummyText1(),
				jane,
				oprah,
				MessageState.UNREAD,
				dateFormat.parse("12:30 24.02.2014"));

		messageDao.save(message);

		message = new Message();
		polyfillMessage(message,
				"You gotta see this",
				"Whats up Oprah?\n\n" + getDummyText2(),
				bernerBaer,
				oprah,
				MessageState.UNREAD,
				dateFormat.parse("11:30 24.02.2014"));

		messageDao.save(message);
	}

	private String getDummyText1() {
		return "I'm very interested in your advertisement, it looks just fabulous. The pictures give a good"
				+ "impression of the room and the whole flat. Your roommates seem to be very nice. The price"
				+ "seems fair. I've been living in many flats, I had a great time with various roommates."
				+ "My cooking skills are seriously impressive :). I like to chill out and drink some beer,"
				+ "have a nice meal together. My hoobies are hiking, traveling, music, sports and books. I"
				+ "really like to visit your flat, I sent you an enquiry for a visit.\n"
				+ "\nBest wishes,\n" + "Jane Doe";
	}

	private String getDummyText2() {
		return "I totally agree with you, glad that we settled this. I can visit the flat another time, this"
				+ "is no problem for me, looking forward to it."
				+ "\nSee you later,\n\n" + "Berner Bär";
	}

	private String getDummyText3() {
		return "Your flat just looks awesome, can't wait to see it myself. Those pictures are really promising,"
				+ "and your roommates seem to be dope too. It would be so leet to chill around and smoke some"
				+ "ganja maybe, good old hippy-style man. I have a doggy, he hurts no one, apart from the police."
				+ "Your roommates seem to be very nice. The price"
				+ "seems fair. I've been living in many flats, I had a great time with various roommates."
				+ "My cooking skills are seriously impressive :). I like to chill out and drink some beer,"
				+ "have a nice meal together. My hoobies are hiking, traveling, music, sports and books. I"
				+ "really like to visit your flat, I sent you an enquiry for a visit.\n"
				+ "\nCheerio,\n" + "Oprah";
	}
}