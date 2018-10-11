package treblewallet.bitbox.util;

public class Utils {
	/**
	 * Convert a raw signature to a DER encoded signature.
	 * @param sig	raw signature
	 * @return		DER encoded signature
	 */
	public static byte[] convertSigFromRawToDER(byte[] sig) {
		byte[] der = new byte[72];
		der[0] = 0x30; // DER sequence
		der[1] = 0x45; // 69 bytes length
		der[2] = 0x02; // an integer value follows
		der[3] = 0x21; // the length of the integer (33 bytes)
		der[4] = 0x00; // r part of the key starts with 0
		System.arraycopy(sig, 0, der, 5, 32); // r value of the signature
		der[37] = 0x02; // another integer value follows
		der[38] = 0x20; // the length of the integer (32 bytes)
		System.arraycopy(sig, 32, der, 39, 32); // s value of the signature
		der[71] = 0x01; // suffix for the type of hash SIGHASH_ALL
		return der;
	}
}
