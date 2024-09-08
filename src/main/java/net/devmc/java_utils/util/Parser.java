package net.devmc.java_utils.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Parser {

	public static String[] tokenize(String input) {
       	return input.split("\\s+");
    }

	public static Map<String, String> parse(String[] args) {
		Map<String, String> arguments = new HashMap<>();

		for (String arg : args) {
			if (arg.startsWith("--")) {
				String[] parts = arg.substring(2).split("=", 2);
				if (parts.length == 2) {
					arguments.put(parts[0], parts[1]);
				} else {
					System.err.println("Invalid argument format: " + arg);
				}
			} else {
				System.err.println("Unknown argument: " + arg);
			}
		}

		return arguments;
	}
}
