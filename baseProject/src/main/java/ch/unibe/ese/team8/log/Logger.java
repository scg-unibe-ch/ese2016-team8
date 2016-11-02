package ch.unibe.ese.team8.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

	private final String toWrite;

	public Logger(final String message, final RequestState state)
	{
		toWrite = createTemplate(message, state);
	}

	/**
	 * TODO: Define template in which things should be added to a file
	 * @param message
	 * @param state
	 * @return
	 */
	private String createTemplate(final String message, final RequestState state)
	{
		// System.lineSepeartor() returns the String which adds a new line
		String toReturn = message.concat(" " + state.toString()).concat(System.lineSeparator());
		return toReturn;
	}

	/**
	 * Logs the current message and state to the given file.
	 *
	 * @param file
	 * @throws IOException
	 */
	public void logToFile(final File file) throws IOException
	{
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(toWrite);
		}
		catch (IOException e)
		{
			throw new IOException(e.toString());
		}
	}
}
