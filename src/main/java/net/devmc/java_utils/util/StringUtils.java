package net.devmc.java_utils.util;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

	private StringUtils() {
		// Utility class, no public constructor
	}

	public static String concentrate(String[] array) {
		return Arrays.toString(array)
				.replace("[", "")
                .replace("]", "")
                .replaceAll(", ", "");
	}

	public static String concentrateWithWhitespaces(String[] array) {
		return Arrays.toString(array)
				.replace("[", "")
				.replace("]", "")
				.replaceAll(",", "");
	}

	public static String concentrate(List<String> list) {
		return Arrays.toString(list.toArray())
				.replace("[", "")
				.replace("]", "")
				.replaceAll(", ", "");
	}

	public static String concentrateWithWhitespaces(List<String> list) {
		return Arrays.toString(list.toArray())
				.replace("[", "")
				.replace("]", "")
				.replaceAll(", ", "");
	}

	public static void main(String[] args) {
		String[] string = {"i", "am", "a", "sentence"};
		String s = concentrate(string);
		System.out.println(s);
	}
}
