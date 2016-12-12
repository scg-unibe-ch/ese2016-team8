package ch.unibe.ese.team8.model;

/**
 * Represents a zip code and the data that belongs to it. Is not marked as
 * entity, since it is <b>not used in Hibernate</b>.
 */
public class Location {

	private int zip;
	private String city;
	private double latitude;
	private double longitude;

	public int getZip()
	{
		return zip;
	}

	public void setZip(final int zip)
	{
		this.zip = zip;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(final String city)
	{
		this.city = city;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(final double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(final double longitude)
	{
		this.longitude = longitude;
	}
}