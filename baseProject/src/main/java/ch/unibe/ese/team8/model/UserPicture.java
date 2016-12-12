package ch.unibe.ese.team8.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Represents a picture that is linked to a <code>user</code>.
 */
@Entity
public class UserPicture extends Picture {

	@OneToOne
	private User user;

	public User getUser()
	{
		return user;
	}

	public void setUser(final User user)
	{
		this.user = user;
	}
}