package treblewallet.bitbox.util;

import java.math.BigInteger;

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

	static public BigInteger convertToBigInteger(String hex) {
		BigInteger n = new BigInteger(hex,16);
		return n;
	}

	static public BigInteger getRFromSig(String sig) {
		return convertToBigInteger(sig.substring(0, 64));
	}

	static public BigInteger getSFromSig(String sig) {
		return convertToBigInteger(sig.substring(64, 128));
	}
}
