package util;

public class BitBoxUtil {
	/**
	 * Rendering of the CLI parameters is different:
	 * 
	 * Windows: """key""":"""value"""
	 * 
	 * Linux: "key":"value"
	 */
	public static final String WINDOWS_ESC_QUOTE = "\"\"\"";
	public static final String UNIX_ESC_QUOTE = "\"";
	private static final String quote = OSValidator.isWindows() ? WINDOWS_ESC_QUOTE : UNIX_ESC_QUOTE;

	public static String createKeyValuePairCLI(String key, String value, String quote) {
		String cli = quote + key + quote + ":" + quote + value + quote;
		return cli;
	}

	/**
	 * Rendering of the CLI parameters is OS dependent. This method escapes the quotes properly.
	 * 
	 * Windows: """key""":"""value"""
	 * 
	 * Linux: "key":"value"
	 */
	public static String createKeyValuePairCLI(String key, String value) {
		return createKeyValuePairCLI(key, value, quote);
	}
}
