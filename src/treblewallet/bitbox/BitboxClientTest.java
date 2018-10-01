package treblewallet.bitbox;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bitcoinj.core.Utils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import treblewallet.bitbox.pojo.HashKeyPathDTO;
import treblewallet.bitbox.pojo.InfoDTO;
import treblewallet.bitbox.pojo.PubKeyDTO;
import treblewallet.bitbox.pojo.PubKeyPathDTO;
import treblewallet.bitbox.pojo.SignDTO;

public class BitboxClientTest {

	private static final String KEY_PATH = "m/44p/1p/0p/0/1";

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
		PubKeyDTO xpub = client.xpub(KEY_PATH);
		log.info("xpub {}: {}", KEY_PATH, xpub);
	}

	/**
	 * Check how many keys per second can be created.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateAdressPerformance() throws Exception {
//		long startMillis = System.currentTimeMillis();
//		int N = 10;
//		for (int i = 0; i < N; i++) {
//			String keyPath = KEY_PATH + "/" + rand.nextInt(1000000);
//			PubKeyDTO xpub = client.xpub(keyPath);
//			log.debug("xpub {}: {}", keyPath, xpub);
//		}
//		long duration = System.currentTimeMillis() - startMillis;
//		float keysPerSec = 1000f * N / duration;
//		log.info("created {} keys in {} ms. {} keys/s: {}", N, duration, keysPerSec);
	}

	String generateRandomHash() {
		byte[] hash = new byte[32];
		for (int i = 0; i < hash.length; i++) {
			hash[i] = (byte) rand.nextInt();
		}
		String hashString = Utils.HEX.encode(hash);
		return hashString;
	}

	@Test
	public void testSigning() throws BitBoxException {
		// PubKeyDTO xpub = client.xpub(KEY_PATH);
		String hash = generateRandomHash();
		String keyPath = KEY_PATH;

		String curve = "secp256k1";
		String meta = null;
		List<HashKeyPathDTO> hashKeyPaths = new LinkedList<>(); 
		hashKeyPaths.add(new HashKeyPathDTO(hash, keyPath));
		List<PubKeyPathDTO> pubKeyPaths = new LinkedList<>(); 
		SignDTO signDTO = client.sign(curve, meta, hashKeyPaths, pubKeyPaths);
		log.info("BITBOX SIGN: {}", signDTO);
		SignDTO signDTO2 = client.sign(curve, meta, hashKeyPaths, pubKeyPaths);
		log.info("BITBOX SIGN: {}", signDTO);
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
