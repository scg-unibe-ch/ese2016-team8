package ch.unibe.ese.team8.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

	/**
	 * Gets the result from the LoggerForm and write it to the 'controller.log'.
	 *
	 * @param file
	 * @throws IOException
	 */
	public void logToFile(final LoggerForm lf) throws IOException
	{
		try {
			File file = new File("baseProject/controller.log");
			FileWriter fw = new FileWriter(file);
			fw.write( lf.getResult() );
			fw.close();
		}
		catch (IOException e)
		{
			throw new IOException(e.toString());
		}
	}
}
