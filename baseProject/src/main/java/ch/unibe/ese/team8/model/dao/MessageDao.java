package ch.unibe.ese.team8.model.dao;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team8.model.Message;
import ch.unibe.ese.team8.model.User;

public interface MessageDao extends CrudRepository<Message, Long> {
	public Iterable<Message> findByRecipient(User user);

	public Iterable<Message> findBySender(User user);

	public Iterable<Message> findByRecipientAndDateSentLessThan(User user, Date date);
}