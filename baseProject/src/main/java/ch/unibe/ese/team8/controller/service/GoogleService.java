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
import ch.unibe.ese.team8.model.UserRole;
import ch.unibe.ese.team8.model.dao.UserDao;

@Service
public class GoogleService {

	@Autowired
	private UserDao userDao;
	
	private static final String DEFAULT_ROLE = "ROLE_USER";
	
	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	private AuthenticationManager authenticationManager;

	/** Handles persisting a new user to the database. */
	@Transactional
	public void saveFrom(GoogleLoginForm googleForm) {
		User user = new User();
		user.setUsername(googleForm.getEmail());
		user.setEmail(googleForm.getEmail());
		user.setFirstName(googleForm.getFirstName());
		user.setLastName(googleForm.getLastName());
		
		final SecureRandom rndm = new SecureRandom();
		String randomPassword = new BigInteger(50, rndm).toString(32);
		user.setPassword(randomPassword); //sets a strong random password
		
		user.setEnabled(true);
		user.setGender(Gender.MALE);
		user.setGoogleUser(true);

		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole(DEFAULT_ROLE);
		role.setUser(user);
		userRoles.add(role);
		
		user.setUserRoles(userRoles);
		
		userDao.save(user);
	}
	
	@Transactional
	public boolean doesUserWithUsernameExist(String username){
		return userDao.findByUsername(username) != null;
	}
	
}
