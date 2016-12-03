package ch.unibe.ese.team8.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Represents a picture that is linked to a user
 */
@Entity
public class UserPicture extends Picture {

	@OneToOne
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}