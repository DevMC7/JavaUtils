package net.devmc.java_utils.logging;

import net.devmc.java_utils.util.Formatting;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@SuppressWarnings("unused")
public class Logger {

	private final String name;
	private final PrintStream out;
	private final DateTimeFormatter dtf;

	public Logger(String name, PrintStream out, DateTimeFormatter dtf) {
		this.name = name;
		this.out = out;
		this.dtf = dtf;
	}

	public Logger(String name, PrintStream out) {
		this(name, out, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	}

	public Logger(String name) {
		this(name, System.out);
	}

	public void info(String message) {
		out.printf("%s[%s] %s[%s] %sINFO: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, name,  Formatting.COLOR.GREEN, Formatting.COLOR.RESET,  message);
	}

	public void debug(String message) {
		out.printf("%s[%s] %s[%s] %sDEBUG: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, name,  Formatting.COLOR.CYAN, Formatting.COLOR.RESET,  message);
	}

	public void warn(String message) {
		out.printf("%s[%s] %s[%s] %sWARN: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, name,  Formatting.COLOR.YELLOW, Formatting.COLOR.RESET,  message);
	}

	public void critical(String message) {
		out.printf("%s[%s] %s[%s] %sCRITICAL: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, name,  Formatting.COLOR.ORANGE, Formatting.COLOR.RESET,  message);
	}

	public void error(String message) {
		out.printf("%s[%s] %s[%s] %sERROR: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, name,  Formatting.COLOR.RED, Formatting.COLOR.RESET,  message);
	}


	public void info(String message, String prefix) {
		out.printf("%s[%s] %s[%s@%s] %sINFO: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, prefix, name, Formatting.COLOR.GREEN, Formatting.COLOR.RESET,  message);
	}

	public void debug(String message, String prefix) {
		out.printf("%s[%s] %s[%s@%s] %sDEBUG: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, prefix, name, Formatting.COLOR.CYAN, Formatting.COLOR.RESET,  message);
	}

	public void warn(String message, String prefix) {
		out.printf("%s[%s] %s[%s@%s] %sWARN: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, prefix, name, Formatting.COLOR.YELLOW, Formatting.COLOR.RESET,  message);
	}

	public void critical(String message, String prefix) {
		out.printf("%s[%s] %s[%s@%s] %sCRITICAL: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, prefix, name, Formatting.COLOR.ORANGE, Formatting.COLOR.RESET,  message);
	}

	public void error(String message, String prefix) {
		out.printf("%s[%s] %s[%s@%s] %sWARN: %s%s%n", Formatting.COLOR.GREEN, LocalDateTime.now().format(this.dtf), Formatting.COLOR.BLUE, prefix, name, Formatting.COLOR.RED, Formatting.COLOR.RESET,  message);
	}
}
