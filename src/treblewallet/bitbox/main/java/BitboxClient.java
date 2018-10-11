import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.HashKeyPathDTO;
import pojo.InfoDTO;
import pojo.PubKeyDTO;
import pojo.PubKeyPathDTO;
import pojo.SignDTO;


/**
 * The {@code BitboxClient} interfaces the Bitbox USB Hardware through the
 * command line. The current implementation supports windows. Linux should work
 * by adapting the folder separation character ('\' to '/').
 * 
 * TODO: Test for linux and adapt where necessary. 
 * 
 * TODO: migrate to use the direct USB interface with JNI
 * using the hidapi interface like the CLI
 * 
 * @author milan
 *
 */
public class BitboxClient {
	private static Logger log = LoggerFactory.getLogger(BitboxClient.class);

	private static final String BITBOX_CLI_LOCATION = "C:\\Users\\milan\\Documents\\eclipse-workspace\\"
			+ "treble-server\\dist\\bitbox\\BitBox_CLI_3.0.0_Win64.exe";
	private String password = "0000";

	static ObjectMapper objectMapper = new ObjectMapper();

	public BitboxClient(String password) {
		super();
		this.password = password;
	}

	private String runCmd(String command, Map<String, String> parameters) throws BitBoxException {
		StringBuffer output = new StringBuffer();
		StringBuffer stdErr = new StringBuffer();
		try {
			// build command
			StringBuffer cmd = new StringBuffer();
			cmd.append(BITBOX_CLI_LOCATION).append(" ");
			String passwordParameter = "-password=\"" + password + "\"";
			cmd.append(passwordParameter).append(" ");
			cmd.append(command).append(" ");
			for (String key : parameters.keySet()) {
				cmd.append("-").append(key).append("=").append(parameters.get(key)).append(" ");
			}

			// Run the cmd string as a Windows command
			log.debug("running {}", cmd);
			Process process = Runtime.getRuntime().exec(cmd.toString());

			// Get input streams
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// Read command standard output
			String s;
			while ((s = stdInput.readLine()) != null) {
				if (s.startsWith("result:")) {
					s = "{";
				}
				output.append(s);
				System.out.println(s);
			}
			log.debug("Result for {}: {}", command, output.toString());

			// Read command errors
			while ((s = stdError.readLine()) != null) {
				stdErr.append(s);
			}
		} catch (Exception e) {
			throw new BitBoxException("Standard error from Bitbox CLI: " + stdErr.toString(), e);
		} finally {
			if (stdErr != null && stdErr.length() > 0) {
				throw new BitBoxException("Standard error from Bitbox CLI: " + stdErr.toString());
			}
		}
		return output.toString();
	}

	/**
	 * Signs an array of hashes for the given key paths and verifies with the public keys, if they are provided.
	 * 
	 * @param curve
	 * @param meta
	 * @param hasharray
	 * @param pubkey
	 * @return
	 * @throws BitBoxException
	 */
	public SignDTO sign(String curve, String meta, List<HashKeyPathDTO> hasharray, List<PubKeyPathDTO> pubkey)
			throws BitBoxException {
		Map<String, String> parameters = new HashMap<>();
		if (curve != null)
			parameters.put("curve", curve);
		if (meta != null)
			parameters.put("meta", meta);
		parameters.put("hasharray", "\"" + hasharray.toString() + "\"");
		if (pubkey != null)
			parameters.put("pubkeyarray", "\"" + pubkey.toString() + "\"");
		String resultString = runCmd("sign", parameters);
		SignDTO result = null;
		try {
			result = objectMapper.readValue(resultString, SignDTO.class);
		} catch (IOException e) {
			log.error("could not convert return String '{}' to SignDTO", resultString, e);
			throw new BitBoxException("could not convert return String '" + resultString + "' to SignDTO", e);
		}
		return result;
	}

	/**
	 * Get the public key for a {@code keyPath}.
	 * 
	 * @param keyPath
	 *            BIP44 {@code keyPath}. e.g. m/44'/1'/0'/0/1
	 * @return
	 * @throws BitBoxException
	 */
	public PubKeyDTO xpub(String keypath) throws BitBoxException {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("keypath", keypath);
		String resultString = runCmd("xpub", parameters);
		PubKeyDTO result = null;
		try {
			result = objectMapper.readValue(resultString, PubKeyDTO.class);
		} catch (IOException e) {
			log.error("could not convert return String '{}' to PubKeyDTO", resultString, e);
			throw new BitBoxException("could not convert return String '" + resultString + "' to PubKeyDTO", e);
		}
		return result;
	}

	/**
	 * Get the Bitbox info.
	 * 
	 * @return
	 * @throws BitBoxException
	 */
	public InfoDTO info() throws BitBoxException {
		String resultString = runCmd("info", new HashMap<String, String>());
		InfoDTO result = null;
		try {
			result = objectMapper.readValue(resultString, InfoDTO.class);
		} catch (IOException e) {
			log.error("could not convert return String '{}' to InfoDTO", result, e);
			throw new BitBoxException("could not convert return String '" + resultString + "' to InfoDTO", e);
		}
		return result;
	}
}
