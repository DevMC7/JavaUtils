package net.devmc.java_utils.logging;

import net.devmc.java_utils.util.Formatting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("unused")
public class Logger {

	public final String name;
	private final PrintStream out;
	private final DateTimeFormatter dtf;
	private PrintStream outFile;
	private boolean appendToFile;

	public Logger(String name, PrintStream out, DateTimeFormatter dtf) {
		this.name = name;
		this.out = out;
		this.dtf = dtf;
		this.appendToFile = true; // Default to append mode
	}

	public Logger(String name, PrintStream out) {
		this(name, out, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	}

	public Logger(String name) {
		this(name, System.out);
	}

	public void setFile(File file, boolean append) {
		closeFile();
		try {
			this.appendToFile = append;
			outFile = new PrintStream(new FileOutputStream(file, appendToFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Failed to set log file: " + file.getAbsolutePath(), e);
		}
	}

	public void setFile(File file) {
		setFile(file, true);
	}

	public void closeFile() {
		if (outFile != null) {
			outFile.close();
			outFile = null;
		}
	}

	public void info(String message) {
		log("INFO", message, Formatting.COLOR.GREEN);
	}

	public void debug(String message) {
		log("DEBUG", message, Formatting.COLOR.CYAN);
	}

	public void warn(String message) {
		log("WARN", message, Formatting.COLOR.YELLOW);
	}

	public void critical(String message) {
		log("CRITICAL", message, Formatting.COLOR.ORANGE);
	}

	public void error(String message) {
		log("ERROR", message, Formatting.COLOR.RED);
	}

	private void log(String level, String message, String color) {
		String formattedMessage = String.format("%s[%s] %s[%s] %s%s: %s%s%n",
				Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf),
				Formatting.COLOR.BLUE, name, color, level,
				Formatting.COLOR.RESET, message);

		out.print(formattedMessage);
		if (outFile != null) {
			outFile.print(stripColors(formattedMessage));
		}
	}

	private String stripColors(String message) {
		return message.replaceAll("\u001B\\[[;\\d]*m", "");
	}

	public void info(String message, String prefix) {
		logWithPrefix("INFO", message, prefix, Formatting.COLOR.GREEN);
	}

	public void debug(String message, String prefix) {
		logWithPrefix("DEBUG", message, prefix, Formatting.COLOR.CYAN);
	}

	public void warn(String message, String prefix) {
		logWithPrefix("WARN", message, prefix, Formatting.COLOR.YELLOW);
	}

	public void critical(String message, String prefix) {
		logWithPrefix("CRITICAL", message, prefix, Formatting.COLOR.ORANGE);
	}

	public void error(String message, String prefix) {
		logWithPrefix("ERROR", message, prefix, Formatting.COLOR.RED);
	}

	private void logWithPrefix(String level, String message, String prefix, String color) {
		String formattedMessage = String.format("%s[%s] %s[%s@%s] %s%s: %s%s%n",
				Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf),
				Formatting.COLOR.BLUE, prefix, name, color, level,
				Formatting.COLOR.RESET, message);

		out.print(formattedMessage);
		if (outFile != null) {
			outFile.print(stripColors(formattedMessage));
		}
	}

	@Override
	protected void finalize() throws Throwable {
		closeFile();
		super.finalize();
	}
}
