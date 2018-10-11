import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bitcoinj.core.Utils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bushidowallet.core.bitcoin.bip32.ECKey;
import com.bushidowallet.core.bitcoin.bip32.ExtendedKey;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import treblewallet.bitbox.main.java.BitboxClient;
import treblewallet.bitbox.main.java.pojo.HashKeyPathDTO;
import treblewallet.bitbox.main.java.pojo.InfoDTO;
import treblewallet.bitbox.main.java.pojo.PubKeyDTO;
import treblewallet.bitbox.main.java.pojo.PubKeyPathDTO;
import treblewallet.bitbox.main.java.pojo.SignDTO;

public class BitboxClientTest {

	private static final boolean DO_PERFORMANCE_TESTING = false;

	private static final String KEY_PATH = "m/44p/1/0/0/1";

	private static final String TEST_HASH = "f6f4a3633eda92eef9dd96858dec2f5ea4dfebb67adac879c964194eb3b97d79";

	private static Logger log = LoggerFactory.getLogger(BitboxClientTest.class);

	private static BitboxClient client;

	private static String HSM_PASSWORD = "0000";

	static Random rand = new Random();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client = new BitboxClient(HSM_PASSWORD);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testInfo() throws Exception {
		InfoDTO info = client.info();
		log.info("info: {}", info.toString());
	}

	@Test
	public void testCreateAddress() throws Exception {
		String keyPath = KEY_PATH + "/" + rand.nextInt(1000000);
		PubKeyDTO xpub = client.xpub(keyPath);
		log.info("xpub {}: {}", KEY_PATH, xpub);
	}

	/**
	 * Check how many keys per second can be created.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCreateAdressPerformance() throws Exception {
		if (DO_PERFORMANCE_TESTING) {
			long startMillis = System.currentTimeMillis();
			int N = 10;
			for (int i = 0; i < N; i++) {
				testCreateAddress();
			}
			long duration = System.currentTimeMillis() - startMillis;
			float keysPerSec = 1000f * N / duration;
			log.info("created {} keys in {} ms. {} keys/s: {}", N, duration, keysPerSec);
			startMillis = System.currentTimeMillis();
			for (int i = 0; i < N; i++) {
				testSigning();
			}
			duration = System.currentTimeMillis() - startMillis;
			keysPerSec = 1000f * N / duration;
			log.info("signed {} hashes in {} ms. {} keys/s: {}", N, duration, keysPerSec);
		}
	}

	@Test
	public void testParseSign() throws JsonParseException, JsonMappingException, IOException {
		String json = "{  \"sign\": [    {      \"sig\": \"11a8d73b55d4517c44b30c4c616ed5d2b9ad6b72966586682dee2f72633098b631d36454cb3c6378c2b48783de4fbe5234ac98bf27f0d1e612066f6ebd75b869\",      \"recid\": \"00\"    }  ]}";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			SignDTO res = objectMapper.readValue(json, SignDTO.class);
			log.info("res: " + res);

		} catch (Exception e) {
			log.error("parse error:", e);
		}
	}

	@Test
	public void testSigning() throws Exception {
		// get pub key
		PubKeyDTO xpub = client.xpub(KEY_PATH);
		log.info("xpub: {}", xpub);
		String pub = xpub.getXpub();
		ExtendedKey key1 = ExtendedKey.parse(pub, true);
		log.info("key1: {}", key1);
		ExtendedKey key2 = ExtendedKey.parse(pub, false);
		log.info("key2: {}", key2);

		// sign message
		String hashString = TEST_HASH;
		String keyPath = KEY_PATH;
		String curve = "secp256k1";
		String meta = null;
		List<HashKeyPathDTO> hashKeyPaths = new LinkedList<>();
		hashKeyPaths.add(new HashKeyPathDTO(hashString, keyPath));
		List<PubKeyPathDTO> pubKeyPaths = new LinkedList<>();
		SignDTO signDTO = client.sign(curve, meta, hashKeyPaths, pubKeyPaths);
		log.info("BITBOX SIGN: {}", signDTO);
		SignDTO signDTO2 = client.sign(curve, meta, hashKeyPaths, pubKeyPaths);
		log.info("BITBOX SIGN: {}", signDTO2);

		// verify signature with public key and signature
		String sigHex = signDTO2.getSign()[0].getSig(); 
		byte[] sig = Utils.HEX.decode(sigHex);
		byte[] sigDes = treblewallet.bitbox.main.java.util.Utils.convertSigFromRawToDER(sig);
		byte[] hash = Utils.HEX.decode(TEST_HASH);
		ECKey eckey = new ECKey(key2.getPublic(), false, false);

		boolean verification = eckey.verify(hash, sigDes);
		log.info("verification successful: {}", verification);
		assertTrue("can't verify signature {" + sigDes + "} with this key: {" + eckey + "}", verification);
	}

	// @Test
	// public void testSigning() {
	// String password = ""; // FIXME: enter password
	// byte[] seed = Utils.HEX.decode(""); // HEX DECODE
	// DeterministicKeyChain keyChain = DeterministicKeyChain.builder()
	// .seed(new DeterministicSeed(seed, password, 100)).build();
	// DeterministicKey key = keyChain.getKey(KeyChain.KeyPurpose.RECEIVE_FUNDS);
	//
	// Transaction transaction = new Transaction(params);
	// Transaction txIn = new Transaction(params, Utils.HEX.decode(""));
	// long index = 0;
	// TransactionOutPoint outPoint = new TransactionOutPoint(params, index, txIn);
	// byte[] pubKeyScript = outPoint.getConnectedPubKeyScript();
	// TransactionInput input = new TransactionInput(params, txIn, pubKeyScript,
	// outPoint);
	// transaction.addInput(input);
	//
	// // String address = "2MxDrG3dvjAAQXpF8eWTQxET5muNaWHprEc"; //out from
	// // BitcoinTransactionTestData.clientPublicKey1,
	// // BitcoinTransactionTestData.clientPublicKey3,
	// // BitcoinTransactionTestData.clientPublicKey2
	// String redeemScript =
	// "524104a48bed6d0c1ff608cfbc4f27d7831061a58c927055d0d74b3ad7351e3523d697785d50acb87c57a472bb9af8dc35cdc10302661fd301e9a58ec414105037a40f41040d4b71cb6c94d6d0decd497adae4ada068f26c47a4cbac51a3cb742f4ec713510924c789ab11d7f986ccf77ac6db3b25b0216d607add7ec083d4b3b7ee0981914104d8448e14c91db4cd7fae958b8d437b73438ba837b2c56fd83bd19936398040fd93fe08dc2478b2cc31d1a6cdd8a32253d99e1c2815cb02e0f08fb1bbb57420fd53ae";
	// Script script = new Script(Utils.HEX.decode(redeemScript));
	// Sha256Hash sighash = transaction.hashForSignature(0, script,
	// Transaction.SigHash.ALL, false);
	//
	// String keyPath = "m/44'/0'/0/0/0"; // TODO: check how many hardened are
	// needed
	// HashKeyPathDTO[] hashKeyPathArray = new HashKeyPathDTO[1];
	// HashKeyPathDTO hashKeyPath = new HashKeyPathDTO();
	// hashKeyPath.setHash(sighash.toString());
	// hashKeyPath.setKeypath(keyPath);
	// hashKeyPathArray[0] = hashKeyPath;
	//
	// PubKeyPathDTO[] checkpubkey = new PubKeyPathDTO[1];
	// PubKeyPathDTO pubkey = new PubKeyPathDTO();
	// pubkey.setPath(keyPath);
	// pubkey.setPubkey(key.getPublicKeyAsHex());
	//
	// checkpubkey[0] = pubkey;
	//
	// SignDTO signDTO = client.sign("secp256k1", "", hashKeyPathArray,
	// checkpubkey);
	//
	// ECKey.ECDSASignature secondSignature =
	// key.getPrivateKeyEncoded(TestNet3Params.get()).getKey().sign(sighash);
	// TransactionSignature transactionSignature = new
	// TransactionSignature(secondSignature, Transaction.SigHash.ALL,
	// false);
	// List<TransactionSignature> signatureList = new ArrayList<>();
	// signatureList.add(transactionSignature);
	// Script inputScript =
	// ScriptBuilder.createP2SHMultiSigInputScript(signatureList, script);
	//
	// log.info("BITBOX SIGN: {}", signDTO.getData()[0].getHash());
	// log.info("BITCOINJ SIGN: {}", inputScript.toString());
	// assertEquals(signDTO.getData()[0].getHash(), inputScript.toString());
	// }
}
