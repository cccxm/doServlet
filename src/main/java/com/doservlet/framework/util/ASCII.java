package com.doservlet.framework.util;

public class ASCII {
	private static final char[] TABLE;
	static {
		TABLE = new char[128];
		for (int i = 0; i < 128; i++) {
			TABLE[i] = (char) i;
		}
	}

	private static char get(int index) {
		return TABLE[index];
	}

	public static char getChar(AsciiDescription description) {
		return get(description.getIndex());
	}

	public static char getChar(int index) {
		return get(index);
	}

	public static String getString(AsciiDescription description) {
		return String.valueOf(get(description.getIndex()));
	}

	public static String getString(int index) {
		return String.valueOf(get(index));
	}
}
