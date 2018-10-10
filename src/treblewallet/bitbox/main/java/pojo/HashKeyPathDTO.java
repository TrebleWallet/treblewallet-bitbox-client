package pojo;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import treblewallet.bitbox.util.BitBoxUtil;

/*
 * Created by Robert Juhas on 7/24/2018
 */
public class HashKeyPathDTO {
	private String hash;
	private String keypath;

	public HashKeyPathDTO(String hash, String keypath) {
		super();
		this.hash = hash;
		this.keypath = keypath;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getKeypath() {
		return keypath;
	}

	public void setKeypath(String keypath) {
		this.keypath = keypath;
	}

	public String toCLIString() {
		return toCLIString(true);
	}

	public String toCLIString(boolean isWindows) {
		StringBuffer sb = new StringBuffer();
		sb.append('{');
		String quote = isWindows ? BitBoxUtil.WINDOWS_ESC_QUOTE : BitBoxUtil.UNIX_ESC_QUOTE;
		sb.append(BitBoxUtil.createKeyValuePairCLI("hash", hash, quote));
		sb.append(",");
		sb.append(BitBoxUtil.createKeyValuePairCLI("keypath", keypath, quote));
		sb.append('}');
		return sb.toString();
	}

	@Override
	public String toString() {
		return toCLIString();
	}

	public static void main(String[] args) throws JsonProcessingException {
		HashKeyPathDTO hashKeyPath1 = new HashKeyPathDTO("12312312312312312312312", "m/44/1/0/0/0");
		HashKeyPathDTO hashKeyPath2 = new HashKeyPathDTO("12312312312312312312312", "m/44/1/0/0/0");
		List<HashKeyPathDTO> hkps = new LinkedList<>();
		hkps.add(hashKeyPath1);
		hkps.add(hashKeyPath2);
		System.out.println(hkps.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		String s = mapper.writeValueAsString(hkps);
		System.out.println(s);
		
	}
}
