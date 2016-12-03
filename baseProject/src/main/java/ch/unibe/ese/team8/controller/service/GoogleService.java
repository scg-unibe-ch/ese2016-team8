package ch.unibe.ese.team8.controller.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team8.controller.pojos.forms.GoogleLoginForm;
import ch.unibe.ese.team8.model.Gender;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.UserPicture;
import ch.unibe.ese.team8.model.UserRole;
import ch.unibe.ese.team8.model.dao.UserDao;

/**
 * The GoogleService class creates a new User with the information provided from
 * the GoogleLoginForm
 */
@Service
public class GoogleService {

	@Autowired
	private UserDao userDao;

	private static final String DEFAULT_ROLE = "ROLE_USER";

	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	private AuthenticationManager authenticationManager;

	/**
	 * Handles persisting a new user to the database.
	 * 
	 * @param googleForm, a GoogleLoginForm.
	 */
	@Transactional
	public void saveFrom(GoogleLoginForm googleForm)
	{
		User user = new User();
		user.setUsername(googleForm.getEmail());
		user.setEmail(googleForm.getEmail());
		user.setFirstName(googleForm.getFirstName());
		user.setLastName(googleForm.getLastName());

		final SecureRandom rndm = new SecureRandom();
		String randomPassword = new BigInteger(50, rndm).toString(32);
		user.setPassword(randomPassword); // sets a strong random password

		user.setEnabled(true);
		user.setGender(Gender.MALE);
		user.setGoogleUser(true);
		UserPicture userPicture = new UserPicture();
		userPicture.setUser(user);
		userPicture.setFilePath(googleForm.getPicture());
		user.setPicture(userPicture);

		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole(DEFAULT_ROLE);
		role.setUser(user);
		userRoles.add(role);

		user.setUserRoles(userRoles);

		userDao.save(user);
	}

	/**
	 * Checks whether a user exists for a given username.
	 * 
	 * @param username
	 * 
	 * @return true, if the user exists.
	 *         false, else.
	 */
	@Transactional
	public boolean doesUserWithUsernameExist(String username)
	{
		return userDao.findByUsername(username) != null;
	}

}
