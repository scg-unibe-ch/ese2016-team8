package ch.unibe.ese.team8.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * Handles all database actions concerning users.
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * Gets the user with the given username.
	 *
	 * @param username
	 *
	 * @return user
	 */
	@Transactional
	public User findUserByUsername(final String username) {
		return userDao.findByUsername(username);
	}

	/**
	 * Gets the user with the given id.
	 *
	 * @param id, long.
	 *
	 * @return user
	 */
	@Transactional
	public User findUserById(final long id) {
		return userDao.findUserById(id);
	}
}