package com.xiaofeng.utils;

/**
 * ****************
 * 
 * @Use:字符处理工具类
 * @Date:2014-2-25
 * @Time:下午3:15:34
 */
public class CharUtils {
	public static final String TAG = "CharUtils";
	private static final String CHAR_STRING = "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007"
			+ "\b\t\n\u000b\f\r\u000e\u000f"
			+ "\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017"
			+ "\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f"
			+ "\u0020\u0021\"\u0023\u0024\u0025\u0026\u0027"
			+ "\u0028\u0029\u002a\u002b\u002c\u002d\u002e\u002f"
			+ "\u0030\u0031\u0032\u0033\u0034\u0035\u0036\u0037"
			+ "\u0038\u0039\u003a\u003b\u003c\u003d\u003e\u003f"
			+ "\u0040\u0041\u0042\u0043\u0044\u0045\u0046\u0047"
			+ "\u0048\u0049\u004a\u004b\u004c\u004d\u004e\u004f"
			+ "\u0050\u0051\u0052\u0053\u0054\u0055\u0056\u0057"
			+ "\u0058\u0059\u005a\u005b\\\u005d\u005e\u005f"
			+ "\u0060\u0061\u0062\u0063\u0064\u0065\u0066\u0067"
			+ "\u0068\u0069\u006a\u006b\u006c\u006d\u006e\u006f"
			+ "\u0070\u0071\u0072\u0073\u0074\u0075\u0076\u0077"
			+ "\u0078\u0079\u007a\u007b\u007c\u007d\u007e\u007f";

	private static final String[] CHAR_STRING_ARRAY = new String[128];
	private static final Character[] CHAR_ARRAY = new Character[128];

	public static final char LF = '\n';
	public static final char CR = '\r';

	static {
		for (int i = 127; i >= 0; i--) {
			CHAR_STRING_ARRAY[i] = CHAR_STRING.substring(i, i + 1);
			CHAR_ARRAY[i] = new Character((char) i);
		}
	}

	private CharUtils() {
		super();
	}

	public static Character toCharacterObject(char ch) {
		if (ch < CHAR_ARRAY.length) {
			return CHAR_ARRAY[ch];
		}
		return new Character(ch);
	}

	public static Character toCharacterObject(String str) {
		if (str.isEmpty()) {
			return null;
		}
		return toCharacterObject(str.charAt(0));
	}

	public static char toChar(Character ch) {
		if (ch == null) {
			throw new IllegalArgumentException("The Character must not be null");
		}
		return ch.charValue();
	}

	public static char toChar(Character ch, char defaultValue) {
		if (ch == null) {
			return defaultValue;
		}
		return ch.charValue();
	}

	public static char toChar(String str) {
		if (str.isEmpty()) {
			throw new IllegalArgumentException("The String must not be empty");
		}
		return str.charAt(0);
	}

	public static char toChar(String str, char defaultValue) {
		if (str.isEmpty()) {
			return defaultValue;
		}
		return str.charAt(0);
	}

	public static int toIntValue(char ch) {
		if (isAsciiNumeric(ch) == false) {
			throw new IllegalArgumentException("The character " + ch
					+ " is not in the range '0' - '9'");
		}
		return ch - 48;
	}

	public static int toIntValue(char ch, int defaultValue) {
		if (isAsciiNumeric(ch) == false) {
			return defaultValue;
		}
		return ch - 48;
	}

	public static int toIntValue(Character ch) {
		if (ch == null) {
			throw new IllegalArgumentException("The character must not be null");
		}
		return toIntValue(ch.charValue());
	}

	public static int toIntValue(Character ch, int defaultValue) {
		if (ch == null) {
			return defaultValue;
		}
		return toIntValue(ch.charValue(), defaultValue);
	}

	public static String toString(char ch) {
		if (ch < 128) {
			return CHAR_STRING_ARRAY[ch];
		}
		return new String(new char[] { ch });
	}

	public static String toString(Character ch) {
		if (ch == null) {
			return null;
		}
		return toString(ch.charValue());
	}

	public static String unicodeEscaped(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		} else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		} else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}

	public static String unicodeEscaped(Character ch) {
		if (ch == null) {
			return null;
		}
		return unicodeEscaped(ch.charValue());
	}

	public static boolean isAscii(char ch) {
		return ch < 128;
	}

	public static boolean isAsciiPrintable(char ch) {
		return ch >= 32 && ch < 127;
	}

	public static boolean isAsciiControl(char ch) {
		return ch < 32 || ch == 127;
	}

	public static boolean isAsciiAlpha(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
	}

	public static boolean isAsciiAlphaUpper(char ch) {
		return ch >= 'A' && ch <= 'Z';
	}

	public static boolean isAsciiAlphaLower(char ch) {
		return ch >= 'a' && ch <= 'z';
	}

	public static boolean isAsciiNumeric(char ch) {
		return ch >= '0' && ch <= '9';
	}

	public static boolean isAsciiAlphanumeric(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
				|| (ch >= '0' && ch <= '9');
	}

	public static boolean isHighSurrogate(char ch) {
		return ('\uD800' <= ch && '\uDBFF' >= ch);
	}

}