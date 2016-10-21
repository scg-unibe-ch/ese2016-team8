package ch.unibe.ese.team8.model.dao;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team8.model.User;

public interface UserDao extends CrudRepository<User, Long> {
	public User findByUsername(String username);

	public User findUserById(long id);

}
