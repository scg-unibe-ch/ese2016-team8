package ch.unibe.ese.team8.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Describes a <code>user</code> on the platform.
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private boolean enabled;

	@Column(nullable = false)
	private boolean premium;

	@Column(nullable = false)
	private boolean googleUser;


	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserRole> userRoles;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private UserPicture picture;

	@Column(nullable = true)
	@Lob
	private String aboutMe;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ad> bookmarkedAds;


	public boolean getPremium()
	{
		return premium;
	}

	public void setPremium(final boolean premium)
	{
		this.premium = premium;
	}

	public long getId()
	{
		return id;
	}

	public void setId(final long id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(final String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(final boolean enabled)
	{
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRoles()
	{
		return userRoles;
	}

	public void setUserRoles(final Set<UserRole> userRoles)
	{
		this.userRoles = userRoles;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public UserPicture getPicture()
	{
		return picture;
	}

	public void setPicture(final UserPicture picture)
	{
		this.picture = picture;
	}

	public Gender getGender()
	{
		return gender;
	}

	public void setGender(final Gender gender)
	{
		this.gender = gender;
	}

	public boolean getGoogleUser()
	{
		return googleUser;
	}

	public void setGoogleUser(final boolean googleUser)
	{
		this.googleUser = googleUser;
	}

	public String getAboutMe()
	{
		return aboutMe;
	}

	public void setAboutMe(final String aboutMe)
	{
		this.aboutMe = aboutMe;
	}

	public List<Ad> getBookmarkedAds()
	{
		return bookmarkedAds;
	}

	public void setBookmarkedAds(final List<Ad> bookmarkedAds)
	{
		this.bookmarkedAds = bookmarkedAds;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * Equals method is defined to check for id only. <p>
	 * Compares the object given as argument to the current object.
	 *
	 * @param object
	 *
	 * @return boolean
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}