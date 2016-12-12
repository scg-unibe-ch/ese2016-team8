package ch.unibe.ese.team8.controller.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team8.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.MessageState;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.MessageDao;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * Handles all persistence operations concerning messaging.
 */
@Service
public class MessageService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageDao messageDao;

	/**
	 * Gets all messages in the inbox of the given user, sorted newest to oldest.
	 *
	 * @param user
	 *
	 * @return Iterable<Message>
	 */
	@Transactional
	public Iterable<Message> getInboxForUser(final User user)
	{
		Iterable<Message> usersMessages = messageDao.findByRecipientAndDateSentLessThan(user, new Date());
		List<Message> messages = new ArrayList<Message>();
		for(Message message: usersMessages)
			messages.add(message);
		Collections.sort(messages, new Comparator<Message>()
		{
			@Override
			public int compare(final Message message1, final Message message2)
			{
				return message2.getDateSent().compareTo(message1.getDateSent());
			}
		});

		if(!messages.isEmpty())
		{
			messages.get(0).setState(MessageState.READ);
			messageDao.save(messages.get(0));
		}

		return messages;
	}

	/**
	 * Gets all messages in the sent folder for the given user.
	 *
	 * @param user
	 *
	 * @return Iterable<Message>
	 */
	@Transactional
	public Iterable<Message> getSentForUser(final User user)
	{
		return messageDao.findBySender(user);
	}

	/**
	 * Gets the message with the given id.
	 *
	 * @param id, the id fo the message.
	 *
	 * @return message
	 */
	@Transactional
	public Message getMessage(final long id)
	{
		return messageDao.findOne(id);
	}

	/**
	 * Handles persisting a new message to the database.
	 *
	 * @param messageForm, the form to take the data from.
	 *
	 * @return Message
	 */
	@Transactional
	public Message saveFrom(final MessageForm messageForm)
	{
		Message message = new Message();

		message.setRecipient(userDao.findByUsername(messageForm.getRecipient()));
		message.setSubject(messageForm.getSubject());
		message.setText(messageForm.getText());
		message.setState(MessageState.UNREAD);

		// Get logged in user as the sender.
		org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		User loggedInUser = userDao.findByUsername(securityUser.getUsername());

		message.setSender(loggedInUser);

		Calendar calendar = Calendar.getInstance();
		// java.util.Calendar uses a month range of 0-11 instead of the
		// XMLGregorianCalendar, which uses 1-12.
		calendar.setTimeInMillis(System.currentTimeMillis());
		message.setDateSent(calendar.getTime());

		messageDao.save(message);

		return message;
	}

	/** Saves a new message with the given parameters in the DB.
	 *
	 * @param sender the user who sends the message.
	 * @param recipient the user who should receive the message.
	 * @param subject the subject of the message.
	 * @param text the text of the message.
	 */
	public void sendMessage(
			final User sender,
			final User recipient,
			final String subject,
			final String text)
	{
		Message message = new Message();
		message.setDateSent(new Date());
		message.setSender(sender);
		message.setRecipient(recipient);
		message.setSubject(subject);
		message.setText(text);
		message.setState(MessageState.UNREAD);

		messageDao.save(message);
	}

	/**
	 * Sets the MessageState of a given Message to "READ".
	 *
	 * @param id
	 */
	@Transactional
	public void readMessage(final long id) {
		Message message = messageDao.findOne(id);
		message.setState(MessageState.READ);
		messageDao.save(message);
	}

	/**
	 * Returns the number of unread messages a user has.
	 *
	 * @param id, a long.
	 */
	@Transactional
	public int unread(final long id) {
		User user = userDao.findOne(id);
		Iterable<Message> usersMessages = messageDao.findByRecipient(user);
		int i = 0;
		for(Message message: usersMessages) {
			if(message.getState().equals(MessageState.UNREAD) && message.getDateSent().before(new Date()))
				i++;
		}
		return i;
	}
}