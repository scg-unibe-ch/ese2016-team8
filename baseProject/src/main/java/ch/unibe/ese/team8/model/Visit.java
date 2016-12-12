package ch.unibe.ese.team8.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A visit for a flat, has a time window.
 */
@Entity
public class Visit {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Ad ad;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> searchers;

	@JsonFormat(pattern = "HH:mm, dd.MM.yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTimestamp;

	@JsonFormat(pattern = "HH:mm, dd.MM.yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTimestamp;

	public long getId()
	{
		return id;
	}

	public void setId(final long id)
	{
		this.id = id;
	}

	public Ad getAd()
	{
		return ad;
	}

	public void setAd(final Ad ad)
	{
		this.ad = ad;
	}

	public List<User> getSearchers()
	{
		return searchers;
	}

	public void setSearchers(final List<User> searchers)
	{
		this.searchers = searchers;
	}

	/**
	 * Used when an enquiry gets accepted.
	 *
	 * @param user
	 */
	public void addToSearchers(final User user)
	{
		searchers.add(user);
	}

	public void removeFromSearchers(final User user)
	{
		searchers.remove(user);
	}

	public Date getStartTimestamp()
	{
		return startTimestamp;
	}

	public void setStartTimestamp(final Date startTimestamp)
	{
		this.startTimestamp = startTimestamp;
	}

	public Date getEndTimestamp()
	{
		return endTimestamp;
	}

	public void setEndTimestamp(final Date endTimestamp)
	{
		this.endTimestamp = endTimestamp;
	}
}