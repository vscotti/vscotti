package com.abc.ceop.util;

public class StringUtil {

	private StringUtil() {
		
	}
	
	public static String removeDiacritics(String input) {
		return input.replaceAll("[\u00e0\u00e1\u00e4]", "a")
					.replaceAll("[\u00e8\u00e9\u00eb]", "e")
					.replaceAll("[\u00ec\u00ed\u00ef]", "i")
					.replaceAll("[\u00f2\u00f3\u00f6]", "o")
					.replaceAll("[\u00f9\u00fa\u00fc]", "u")
					.replaceAll("[\u00c0\u00c1\u00c4]", "A")
					.replaceAll("[\u00c8\u00c9\u00eb]", "E")
					.replaceAll("[\u00cc\u00cd\u00cf]", "I")
					.replaceAll("[\u00d2\u00d3\u00df]", "O")
					.replaceAll("[\u00d9\u00da\u00dc]", "U");
	}
	
}
