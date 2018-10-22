package treblewallet.bitbox;

import com.bushidowallet.core.bitcoin.bip32.ECKey;
import com.bushidowallet.core.bitcoin.bip32.ExtendedKey;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.math.ec.ECPoint;
import treblewallet.bitbox.pojo.HashKeyPathDTO;
import treblewallet.bitbox.pojo.InfoDTO;
import treblewallet.bitbox.pojo.PubKeyDTO;
import treblewallet.bitbox.pojo.PubKeyPathDTO;
import treblewallet.bitbox.pojo.SignDTO;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class BitboxClientTest {

    private static final boolean DO_PERFORMANCE_TESTING = false;

    private static final String KEY_PATH = "m/44p/1/0/0/1";

    private static final String BITBOX_CLI_LOCATION = "C:\\Users\\milan\\Documents\\eclipse-workspace\\"
            + "treble-server\\dist\\bitbox\\BitBox_CLI_3.0.0_Win64.exe";

    private static final String TEST_HASH = "f6f4a3633eda92eef9dd96858dec2f5ea4dfebb67adac879c964194eb3b97d79";
    static Random rand = new Random();
    private static Logger log = LoggerFactory.getLogger(BitboxClientTest.class);
    private static BitboxClient client;
    private static String HSM_PASSWORD = "0000";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        client = new BitboxClient(HSM_PASSWORD, BITBOX_CLI_LOCATION);
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

    @Test
    public void testGetPublicKeyTestNet() throws Exception{
        String keyPath = "m/44h/1h/0/0/0";
        PubKeyDTO publicKey = client.xpub(keyPath);
        ExtendedKey key = ExtendedKey.parse(publicKey.getXpub(), true);
        System.out.println(Utils.HEX.encode(key.getPublic()));
        System.out.println(key.getPublicHex());
        System.out.println(key.serializePublic());
    }

    @Test
    public void testGetPublicKeyMainNet() {
        String keyPath = "m/44h/0h/0/0/0";
        PubKeyDTO publicKey = client.xpub(keyPath);
        DeterministicKey key = DeterministicKey.deserializeB58(publicKey.getXpub(), MainNetParams.get());
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
        byte[] sigDes = treblewallet.bitbox.util.Utils.convertSigFromRawToDER(sig);
        byte[] hash = Utils.HEX.decode(TEST_HASH);
        ECKey eckey = new ECKey(key2.getPublic(), false, false);

        boolean verification = eckey.verify(hash, sigDes);
        log.info("verification successful: {}", verification);
        assertTrue("can't verify signature {" + sigDes + "} with this key: {" + eckey + "}", verification);
    }

    @Test
    public void testCreatingMultisigAddress() throws Exception {
        NetworkParameters params = TestNet3Params.get();
        String rawTransaction = "0200000001d59d012e2cfd8ff9dab438cb04378286da988f4c4e86d49dfc6024a891feb7890000000017160014757d1f8fc2d020e9ccc25d05f89b0abf874db8d2fdffffff02a3e5ba000000000017a914a0ac976efef4851be20aa4b266dae4fe83210fd087a08601000000000017a914af523647d560101aa103a888d53c3324e1726f2b8755f81500";
        org.bitcoinj.core.ECKey publicKey1 = org.bitcoinj.core.ECKey.fromPublicOnly(Utils.HEX.decode("04A48BED6D0C1FF608CFBC4F27D7831061A58C927055D0D74B3AD7351E3523D697785D50ACB87C57A472BB9AF8DC35CDC10302661FD301E9A58EC414105037A40F".toLowerCase())); //testnet key
        PubKeyDTO pubKeyDTO = client.xpub(KEY_PATH);
        ExtendedKey extendedKey = ExtendedKey.parse(pubKeyDTO.getXpub(), false);
        org.bitcoinj.core.ECKey publicKey2 = org.bitcoinj.core.ECKey.fromPublicOnly(extendedKey.getPublic()); //testnet key
        org.bitcoinj.core.ECKey publicKey3 = org.bitcoinj.core.ECKey.fromPublicOnly(Utils.HEX.decode("040D4B71CB6C94D6D0DECD497ADAE4ADA068F26C47A4CBAC51A3CB742F4EC713510924C789AB11D7F986CCF77AC6DB3B25B0216D607ADD7EC083D4B3B7EE098191".toLowerCase())); //testnet key
        List<org.bitcoinj.core.ECKey> keys = ImmutableList.of(publicKey1, publicKey3, publicKey2);
        Script redeemScript = ScriptBuilder.createMultiSigOutputScript(2, keys);
        Script scriptPubKey = ScriptBuilder.createP2SHOutputScript(redeemScript);
        Address address = Address.fromP2SHScript(params, scriptPubKey);
        String redeemScriptString = Utils.HEX.encode(redeemScript.getProgram());
        String addressString = address.toBase58();
        System.out.println(addressString);
        Transaction previousTransaction = new Transaction(params, Utils.HEX.decode(rawTransaction));
        TransactionOutPoint outPoint = new TransactionOutPoint(params, 1, previousTransaction);
        TransactionInput inputTransaction = new TransactionInput(params, previousTransaction, outPoint.getConnectedPubKeyScript(), outPoint);

        Transaction transaction = new Transaction(params);
        transaction.addInput(inputTransaction);
        transaction.addOutput(Coin.valueOf(8000), Address.fromBase58(params, "mofhdVSgsUsVacWsf8QMNhDQqYnVXPtnZH"));

        System.out.println("RAW TRANSACTION AFTER INPUTS AND OUTPUTS: " + Utils.HEX.encode(transaction.bitcoinSerialize()));

        inputTransaction.getOutpoint().getHash().toString();
        Sha256Hash sighash = transaction.hashForSignature(0, redeemScript, Transaction.SigHash.ALL, false);

        org.bitcoinj.core.ECKey key1 = DumpedPrivateKey.fromBase58(params, "92CqrxbHxU1nDQo2N9UkL6bGftcfbh929cYzPSubPshyERqhUK7").getKey();
        org.bitcoinj.core.ECKey.ECDSASignature firstSignature = key1.sign(sighash);

        // Add the first signature to the signature list
        TransactionSignature signature = new TransactionSignature(firstSignature, Transaction.SigHash.ALL, false);
        List<TransactionSignature> signatures = new ArrayList<>();
        signatures.add(signature);
        // Rebuild p2sh multisig input script = unlocking script
        Script inputScript = ScriptBuilder.createP2SHMultiSigInputScript(signatures, redeemScript);
        transaction.getInput(0).setScriptSig(inputScript);
        System.out.println("RAW TRANSACTION AFTER 1ST SIGNATURE: " + Utils.HEX.encode(transaction.bitcoinSerialize()));

        Sha256Hash sighash2 = transaction.hashForSignature(0, redeemScript, Transaction.SigHash.ALL, false);

        List<HashKeyPathDTO> hashArray = new ArrayList<HashKeyPathDTO>();
        hashArray.add(new HashKeyPathDTO(sighash2.toString(), KEY_PATH));
        List pubKeyArray = new ArrayList();
        SignDTO signDTO = client.sign("secp256k1", null, hashArray, pubKeyArray);
        signDTO = client.sign("secp256k1", null, hashArray, pubKeyArray);
        String rString = signDTO.getSign()[0].getSig().substring(0,64);
        String sString = signDTO.getSign()[0].getSig().substring(64,128);
        BigInteger r = new BigInteger(Utils.HEX.decode(rString));
        BigInteger s = new BigInteger(Utils.HEX.decode(sString));
        org.bitcoinj.core.ECKey.ECDSASignature signature2 = new org.bitcoinj.core.ECKey.ECDSASignature(r,s);
        // Add the second signature to the signature list
        signature = new TransactionSignature(signature2, Transaction.SigHash.ALL, false);
        signatures.add(signature);
        inputScript = ScriptBuilder.createP2SHMultiSigInputScript(signatures, redeemScript);
        transaction.getInput(0).setScriptSig(inputScript);

        System.out.println("RAW TRANSACTION AFTER 2ND SIGNATURE: " + Utils.HEX.encode(transaction.bitcoinSerialize()));
    }

    @Test(expected = BitBoxException.class)
    public void testNullPassword() throws BitBoxException {
        new BitboxClient(null,null);
    }

    @Test(expected = BitBoxException.class)
    public void testEmptyPassword() throws BitBoxException {
        new BitboxClient("",null);
    }

    @Test(expected = BitBoxException.class)
    public void testNullPath() throws BitBoxException {
        new BitboxClient(HSM_PASSWORD,null);
    }

    @Test(expected = BitBoxException.class)
    public void testEmptyPath() throws BitBoxException {
        new BitboxClient(HSM_PASSWORD,"");
    }

    @Test(expected = BitBoxException.class)
    public void testWrongPassword() throws BitBoxException {
        BitboxClient client = new BitboxClient("1234","somepath");
        client.info();
    }

    @Test(expected = BitBoxException.class)
    public void testWrongPath() throws BitBoxException {
        BitboxClient client = new BitboxClient(HSM_PASSWORD,"somepath");
        client.info();
    }

    @Test(expected = BitBoxException.class)
    public void testWrongData() throws BitBoxException {
        client.sign("", "", null, null);
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
