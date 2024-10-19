package net.devmc.java_utils.logging;

public class LogManager {

	public static Logger getLogger(Class<?> clazz) {
		String[] packages = clazz.getName().split("\\.");
		return new Logger(packages[packages.length - 1]);
	}
}
