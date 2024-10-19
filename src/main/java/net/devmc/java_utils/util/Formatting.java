package net.devmc.java_utils.util;

public final class Formatting {

	private Formatting() {
		// Utility class, no public constructor
	}

	public static class COLOR {
		public static final String RESET = "\033[0m";
		public static final String RED = "\033[0;31m";
		public static final String GREEN = "\033[0;32m";
		public static final String YELLOW = "\033[0;33m";
		public static final String BLUE = "\033[0;34m";
		public static final String PURPLE = "\033[0;35m";
		public static final String CYAN = "\033[0;36m";
		public static final String ORANGE = "\033[38;5;214m";
	}
}
