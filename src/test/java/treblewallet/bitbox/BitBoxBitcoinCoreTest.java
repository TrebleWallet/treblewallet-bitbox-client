package treblewallet.bitbox;

import bcJsonRpc.BitcoinCoreConfig;
import bcJsonRpc.BitcoindInterface;
import bcJsonRpc.pojo.ListUnspentTO;
import bcJsonRpc.pojo.RawTransaction;
import com.bushidowallet.core.bitcoin.bip32.ExtendedKey;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
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
import treblewallet.bitbox.pojo.PubKeyPathDTO;
import treblewallet.bitbox.pojo.SignDTO;
import treblewallet.bitbox.util.BitBoxUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assume.assumeTrue;

/*
 * Created by Robert Juhas on 10/24/2018
 */
public class BitBoxBitcoinCoreTest {
    private static Logger log = LoggerFactory.getLogger(BitBoxBitcoinCoreTest.class);

    static BitcoindInterface bitcoinCoreClient;
    static BitcoinCoreConfig config;
    private static final String BITBOX_CLI_LOCATION = "/home/robert/treblewallet-hsm/BitBox_CLI_3.0.0_Linux64";

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
        try{
            bitcoinCoreClient.getblockchaininfo();
        } catch (Exception e) {
            e.printStackTrace();
            assumeTrue(false);
        }
        client = new BitboxClient(HSM_PASSWORD, BITBOX_CLI_LOCATION, null);
        params = TestNet3Params.get();
        KEY_PATH = "m/44p/1/0/0/9";
        org.bitcoinj.core.ECKey publicKey1 = org.bitcoinj.core.ECKey.fromPublicOnly(Utils.HEX.decode(BitcoinCoreConfig.clientPublicKey1.toLowerCase()));
        PubKeyDTO pubKeyDTO = client.xpub(KEY_PATH);
        ExtendedKey extendedKey = ExtendedKey.parse(pubKeyDTO.getXpub(), false);
        org.bitcoinj.core.ECKey publicKey2 = org.bitcoinj.core.ECKey.fromPublicOnly(extendedKey.getPublic());
        System.out.println("PUBLIC KEY 2: " + publicKey2.getPublicKeyAsHex());
        System.out.println("PUBLIC EXTENDED KEY 2: " + publicKey2.decompress().getPublicKeyAsHex());
        org.bitcoinj.core.ECKey publicKey3 = org.bitcoinj.core.ECKey.fromPublicOnly(Utils.HEX.decode(BitcoinCoreConfig.clientPublicKey3.toLowerCase()));
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
        unspents.sort((x,y) -> x.getTxid().compareTo(y.getTxid()));
        unspents.forEach(x -> System.out.println(x.getTxid()));
    }

    @Test
    public void spendingTransactions() {
        for (int i = 0; i < 10; i++) {
            try {
                createMultisigTransactionHSM(i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    //@Test
    public void createMultisigTransactionHSM(int i) {
        Assume.assumeNotNull(addressString);
        Assume.assumeTrue(!addressString.isEmpty());
        Assume.assumeTrue(!unspents.isEmpty());
        ListUnspentTO unspentTO = unspents.get(i);
        String inputTransactionBitcoinCoreHEX = bitcoinCoreClient.getrawtransaction(unspentTO.getTxid());

        System.out.println("UTXO ID:" + unspentTO.getTxid() + " INDEX: " + unspentTO.getVout());

        Transaction previousTransaction = new Transaction(params, Utils.HEX.decode(inputTransactionBitcoinCoreHEX));
        TransactionOutPoint outPoint = new TransactionOutPoint(params, unspentTO.getVout(), previousTransaction);
        TransactionInput inputTransaction = new TransactionInput(params, previousTransaction, outPoint.getConnectedPubKeyScript(), outPoint);

        Transaction transaction = new Transaction(params);
        transaction.addInput(inputTransaction);
        transaction.addOutput(Coin.valueOf(8000), Address.fromBase58(params, "mofhdVSgsUsVacWsf8QMNhDQqYnVXPtnZH"));

        inputTransaction.getOutpoint().getHash().toString();
        Sha256Hash sighash = transaction.hashForSignature(0, redeemScript, Transaction.SigHash.ALL, false);
        log.info("SIGNATURE HASH: {}", sighash);
        org.bitcoinj.core.ECKey key1 = DumpedPrivateKey.fromBase58(params, "92CqrxbHxU1nDQo2N9UkL6bGftcfbh929cYzPSubPshyERqhUK7").getKey();
        org.bitcoinj.core.ECKey.ECDSASignature firstSignature = key1.sign(sighash);

        // Add the first signature to the signature list
        TransactionSignature signature = new TransactionSignature(firstSignature, Transaction.SigHash.ALL, false);
        List<TransactionSignature> signatures = new ArrayList<>();
        signatures.add(signature);

        List<HashKeyPathDTO> hashArray = new ArrayList<HashKeyPathDTO>();
        hashArray.add(new HashKeyPathDTO(sighash.toString(), KEY_PATH));
        SignDTO signDTO = client.sign("secp256k1", null, hashArray, new ArrayList<PubKeyPathDTO>());
        signDTO = client.sign("secp256k1", null, hashArray, new ArrayList<PubKeyPathDTO>());
        BigInteger r = BitBoxUtil.getRFromSig(signDTO.getSign()[0].getSig());
        BigInteger s = BitBoxUtil.getSFromSig(signDTO.getSign()[0].getSig());
        org.bitcoinj.core.ECKey.ECDSASignature signature2 = new org.bitcoinj.core.ECKey.ECDSASignature(r, s);
        // Add the second signature to the signature list
        signature = new TransactionSignature(signature2, Transaction.SigHash.ALL, false);
        signatures.add(signature);
        Script inputScript = ScriptBuilder.createP2SHMultiSigInputScript(signatures, redeemScript);
        System.out.println("INPUT SCRIPT: " + Utils.HEX.encode(inputScript.getProgram()));
        transaction.getInput(0).setScriptSig(inputScript);

        String rawTransactionHEX = Utils.HEX.encode(transaction.bitcoinSerialize());
        log.info("RAW TRANSACTION: {}", rawTransactionHEX);
        RawTransaction rawTransaction = bitcoinCoreClient.decoderawtransaction(rawTransactionHEX);
        long matches = StringUtils.countMatches(rawTransaction.getVin().get(0).getScriptSig().getAsm(), "[ALL]");
        System.out.println("NUMBER OF ALL SIGNATURES: " + matches);
        bitcoinCoreClient.sendrawtransaction(rawTransactionHEX);
    }
}
