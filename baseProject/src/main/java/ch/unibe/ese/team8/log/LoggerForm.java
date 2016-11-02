package ch.unibe.ese.team8.log;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Saves all the strings it has been given including a <code>RequestState</code>
 *
 */
public class LoggerForm
{
	private String header = "";
	private String body;
	private String result = "";

	public void createHeader(final RequestState state)
	{
		// Fills first line of toReturn with the State and adds a new line at the end
		// Adds the current Date to the toReturn
		header = String.format("State: %s %s Date: %s", state.toString(), System.lineSeparator(), Calendar.getInstance().getTime().toString());
	}
	public void createBody(final String... args)
	{
		// Streams args and for every String adds him to to Return
		Arrays.stream(args).forEach(s -> (body + "").concat(String.format("%s %s", s, System.lineSeparator())));
	}

	public void createResult()
	{
		result = header + body;
	}

	public String getResult()
	{
		return result;
	}

	public void clear()
	{
		header = "";
		body = "";
		result = "";
	}
}
