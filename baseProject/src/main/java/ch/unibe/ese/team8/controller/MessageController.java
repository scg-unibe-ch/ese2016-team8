package ch.unibe.ese.team8.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team8.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team8.controller.service.MessageService;
import ch.unibe.ese.team8.controller.service.UserService;
import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.User;

/**
 * This controller handles all requests concerning messaging.
 */
@Controller
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	/**
	 * Shows the messages page for the currently logged in user. The inbox of
	 * the user is shown.
	 * 
	 * @param principal
	 * 
	 * @return model, the modified ModelAndView instance.
	 */
	@RequestMapping(value = "/profile/messages", method = RequestMethod.GET)
	public ModelAndView messages(final Principal principal)
	{
		ModelAndView model = new ModelAndView("messages");
		User user = userService.findUserByUsername(principal.getName());
		model.addObject("messageForm", new MessageForm());
		model.addObject("messages", messageService.getInboxForUser(user));
		return model;
	}

	/**
	 * Gets all messages in the inbox for the currently logged in user.
	 * 
	 * @param principal
	 * 
	 * @return Iterable<Message>, retrieved by 
	 *         <code>MessageService#getInboxForuser(user)</code>
	 */
	@RequestMapping(value = "/profile/message/inbox", method = RequestMethod.POST)
	public @ResponseBody Iterable<Message> getInbox(final Principal principal)
	{
		User user = userService.findUserByUsername(principal.getName());
		return messageService.getInboxForUser(user);
	}

	/**
	 * Gets all messages in the sent folder for the currently logged in user.
	 * 
	 * @param principal
	 * 
	 * @return Iterable<Message>, retrieved from
	 *         <code>MessageService#getSentForUser(user)</code>
	 */
	@RequestMapping(value = "/profile/message/sent", method = RequestMethod.POST)
	public @ResponseBody Iterable<Message> getSent(final Principal principal)
	{
		User user = userService.findUserByUsername(principal.getName());
		return messageService.getSentForUser(user);
	}

	/**
	 * Gets the message with the given id.
	 * 
	 * @param id, the id of the message to retrieve.
	 * 
	 * @return Message, from <code>MessageService#getMessage(id)</code>
	 */
	@RequestMapping(value = "/profile/messages/getMessage", method = RequestMethod.GET)
	public @ResponseBody Message getMessage(@RequestParam final Long id)
	{
		return messageService.getMessage(id);
	}

	/**
	 * Shows the messages page and validates and/or saves the message passed a
	 * post data.
	 * 
	 * @param MessageForm
	 * @param bindingResult
	 * @param principal
	 * 
	 * @return if 'bindingResult' has Errors, a new instance of
	 *         <code>ModelAndView('messages') is returned,
	 *         else we modify the model retrieved from <code>ModelAndView('messages')</code>.
	 * 
	 */
	@RequestMapping(value = "/profile/messages", method = RequestMethod.POST)
	public ModelAndView messageSent(
			@Valid final MessageForm messageForm,
			final BindingResult bindingResult,
			final Principal principal)
	{
		ModelAndView model = new ModelAndView("messages");
		if (!bindingResult.hasErrors()) {
			messageService.saveFrom(messageForm);
			User user = userService.findUserByUsername(principal.getName());
			model.addObject("messageForm", new MessageForm());
			model.addObject("messages", messageService.getInboxForUser(user));
		}
		return model;
	}

	/**
	 * Sets the MessageState of a given Message to "READ".
	 * 
	 * @param id, the id of the message.
	 */
	@RequestMapping(value="/profile/readMessage", method = RequestMethod.GET)
	public @ResponseBody void readMessage(@RequestParam("id") final long id)
	{
		messageService.readMessage(id);
	}

	/**
	 * Returns the number of unread messages of the logged in user. Uses the principal
	 * to retrieve the id.
	 * 
	 * @param principal
	 * 
	 * @return int, number of unread messages retrieved from
	 *         <code>MessageService#unread(id)</code>
	 */
	@RequestMapping(value="/profile/unread", method = RequestMethod.GET)
	public @ResponseBody int unread(final Principal principal)
	{
		long id = userService.findUserByUsername(principal.getName()).getId();
		return messageService.unread(id);
	}

	/**
	 * Checks if the email passed as post parameter is a valid email. In case it
	 * is valid, the email address is returned. If it is not, a error message is
	 * returned.
	 * 
	 * @param email, a String
	 * 
	 * @retun String, either a simple error message 'this user does not exist',
	 *                else <code>user.getEmail()</code>.
	 */
	@RequestMapping(value = "/profile/messages/validateEmail", method = RequestMethod.POST)
	@ResponseBody
	public String validateEmail(@RequestParam final String email)
	{
		User user = userService.findUserByUsername(email);
		if (user == null)
		{
			return "This user does not exist.";
		} else {
			return user.getEmail();
		}
	}

	/**
	 * Sends a message with the passed parameters
	 * 
	 * @param subject, a String for the subject of the message.
	 * @param text
	 * @param recipientEmail
	 * @param principal
	 */
	@RequestMapping(value = "/profile/messages/sendMessage", method = RequestMethod.POST)
	public @ResponseBody void sendMessage(
			@RequestParam final String subject,
			@RequestParam final String text,
			@RequestParam final String recipientEmail,
			final Principal principal)
	{
		User recipient = userService.findUserByUsername(recipientEmail);
		User sender = userService.findUserByUsername(principal.getName());
		messageService.sendMessage(sender, recipient, subject, text);
	}

}
