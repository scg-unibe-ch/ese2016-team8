package ch.unibe.ese.team8.model;

/**
 * Describes a picture with properties such as filename, file size etc. Objects
 * of this type should be <b>convertable to JSON</b>. That is also the reason why
 * fileSize is a String attribute.
 */
public class PictureMeta {

	private String name;
	private String size;
	private String type;
	private String url;

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize(final String size)
	{
		this.size = size;
	}

	public String getType()
	{
		return type;
	}

	public void setType(final String type)
	{
		this.type = type;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(final String url)
	{
		this.url = url;
	}
}