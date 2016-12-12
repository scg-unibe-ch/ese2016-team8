package ch.unibe.ese.team8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Describes a <code>rating</code> that was given to a ratee by a rater.
 */
@Entity
public class Rating {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private User rater;

	@ManyToOne
	private User ratee;

	@Column(nullable = false)
	private int rating;

	public long getId()
	{
		return id;
	}

	public void setId(final long id)
	{
		this.id = id;
	}

	public User getRater()
	{
		return rater;
	}

	public void setRater(final User rater)
	{
		this.rater = rater;
	}

	public User getRatee()
	{
		return ratee;
	}

	public void setRatee(final User ratee)
	{
		this.ratee = ratee;
	}

	public int getRating()
	{
		return rating;
	}

	public void setRating(final int rating)
	{
		this.rating = rating;
	}
}