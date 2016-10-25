package ch.unibe.ese.team8.controller.pojos.forms;

import org.hibernate.validator.constraints.NotBlank;

/** This form is used when a user wants to edit their profile. */
public class EditProfileForm {

	@NotBlank(message = "Required")
	private String username;

	@NotBlank(message = "Required")
	private String password;

	@NotBlank(message = "Required")
	private String firstName;

	@NotBlank(message = "Required")
	private String lastName;

	private String aboutMe;


	private boolean premium;

	public boolean getPremium(){
		return premium;
	}

	public void setPremium(final boolean premium){
		this.premium= premium;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(final String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
