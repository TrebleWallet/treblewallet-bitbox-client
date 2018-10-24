package treblewallet.bitbox;

import bcJsonRpc.BitcoinCoreConfig;
import bcJsonRpc.BitcoindInterface;
import bcJsonRpc.pojo.ListUnspentTO;
import bcJsonRpc.pojo.RawTransaction;
import com.bushidowallet.core.bitcoin.bip32.ExtendedKey;
import com.google.common.collect.ImmutableList;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import treblewallet.bitbox.pojo.HashKeyPathDTO;
import treblewallet.bitbox.pojo.PubKeyDTO;
import treblewallet.bitbox.pojo.SignDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Created by Robert Juhas on 10/24/2018
 */
public class BitBoxBitcoinCoreTest {
    private static Logger log = LoggerFactory.getLogger(BitBoxBitcoinCoreTest.class);

    static BitcoindInterface bitcoinCoreClient;
    static BitcoinCoreConfig config;
    static final String BITBOX_CLI_LOCATION = "C:\\Work\\Projects\\TrebleWallet\\TrebleWallet\\BitBox_CLI_3.0.0_Win64.exe";

    private static BitboxClient client;
    private static String HSM_PASSWORD = "0000";
    private static NetworkParameters params;
    private static String addressString;
    private static List<ListUnspentTO> unspents;
    private static Script redeemScript;
    private static String KEY_PATH;

    @BeforeClass
    public static void setup() throws Exception {
        config = new BitcoinCoreConfig();
        bitcoinCoreClient = config.getClient();
        client = new BitboxClient(HSM_PASSWORD, BITBOX_CLI_LOCATION);
        params = TestNet3Params.get();
        KEY_PATH = "m/44p/1/0/0/9";
        org.bitcoinj.core.ECKey publicKey1 = org.bitcoinj.core.ECKey.fromPublicOnly(Utils.HEX.decode(config.clientPublicKey1.toLowerCase()));
        PubKeyDTO pubKeyDTO = client.xpub(KEY_PATH);
        ExtendedKey extendedKey = ExtendedKey.parse(pubKeyDTO.getXpub(), false);
        org.bitcoinj.core.ECKey publicKey2 = org.bitcoinj.core.ECKey.fromPublicOnly(extendedKey.getPublic());
        org.bitcoinj.core.ECKey publicKey3 = org.bitcoinj.core.ECKey.fromPublicOnly(Utils.HEX.decode(config.clientPublicKey3.toLowerCase()));
        List<ECKey> keys = ImmutableList.of(publicKey1, publicKey3, publicKey2);
        redeemScript = ScriptBuilder.createMultiSigOutputScript(2, keys);
        Script scriptPubKey = ScriptBuilder.createP2SHOutputScript(redeemScript);
        Address address = Address.fromP2SHScript(params, scriptPubKey);
        addressString = address.toBase58();
        System.out.println(addressString);
        List<String> addresses = new ArrayList<>();
        addresses.add(addressString);
        bitcoinCoreClient.importaddress(addressString, null, false, false);
        unspents = bitcoinCoreClient.listunspent(0, 99999, addresses, false, new HashMap<>());
        if (unspents.size() == 0) {
            for (int i = 0; i < 100; i++) {
                bitcoinCoreClient.sendtoaddress(addressString, new BigDecimal("0.0001").setScale(8, RoundingMode.DOWN));
            }
        }
    }

    @Test
    public void createMultisigTransactionHSM() throws Exception {
        Assume.assumeNotNull(addressString);
        Assume.assumeTrue(!addressString.isEmpty());
        Assume.assumeTrue(!unspents.isEmpty());
        ListUnspentTO unspentTO = unspents.get(0);
        String inputTransactionBitcoinCoreHEX = bitcoinCoreClient.getrawtransaction(unspentTO.getTxid());
        RawTransaction inputTransactionBitcoinCoreVERBOSE = bitcoinCoreClient.getrawtransaction(unspentTO.getTxid(), true);
        Transaction previousTransaction = new Transaction(params, Utils.HEX.decode(inputTransactionBitcoinCoreHEX));
        TransactionOutPoint outPoint = new TransactionOutPoint(params, unspentTO.getVout(), previousTransaction);
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
        String rString = signDTO.getSign()[0].getSig().substring(0, 64);
        String sString = signDTO.getSign()[0].getSig().substring(64, 128);
        BigInteger r = new BigInteger(Utils.HEX.decode(rString));
        BigInteger s = new BigInteger(Utils.HEX.decode(sString));
        org.bitcoinj.core.ECKey.ECDSASignature signature2 = new org.bitcoinj.core.ECKey.ECDSASignature(r, s);
        // Add the second signature to the signature list
        signature = new TransactionSignature(signature2, Transaction.SigHash.ALL, false);
        signatures.add(signature);
        inputScript = ScriptBuilder.createP2SHMultiSigInputScript(signatures, redeemScript);
        transaction.getInput(0).setScriptSig(inputScript);

        bitcoinCoreClient.sendrawtransaction(Utils.HEX.encode(transaction.bitcoinSerialize()));
    }
}
