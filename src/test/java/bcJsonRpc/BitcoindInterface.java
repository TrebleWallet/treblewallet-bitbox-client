package bcJsonRpc;

import bcJsonRpc.pojo.Account;
import bcJsonRpc.pojo.Address;
import bcJsonRpc.pojo.AddressInformation;
import bcJsonRpc.pojo.Block;
import bcJsonRpc.pojo.BlockChainInfo;
import bcJsonRpc.pojo.BlockVerbose;
import bcJsonRpc.pojo.CreateAddress;
import bcJsonRpc.pojo.DecodedScript;
import bcJsonRpc.pojo.FeeDTO;
import bcJsonRpc.pojo.LastBlock;
import bcJsonRpc.pojo.ListUnspentTO;
import bcJsonRpc.pojo.RawTransaction;
import bcJsonRpc.pojo.RawTxInput;
import bcJsonRpc.pojo.RawTxOutput;
import bcJsonRpc.pojo.Transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface BitcoindInterface {
	//Add a nrequired-to-sign multisignature address to the wallet. Each key is a bitcoin address or hex-encoded  key.
	String addmultisigaddress(int nrequired, String keys);
	//If [account] is specified, assign address to [account].
	String addmultisigaddress(int nrequired, String keys, String account);
	// Returns hash of block in best-block-chain at height provided.
	String getblockhash(int height);
	//Returns an object containing various state info.
	BlockChainInfo getblockchaininfo();
	//Safely copies wallet.dat to destination, which can be a directory or a path with filename.
	boolean backupwallet();
	//Removes the wallet encryption key from memory, locking the wallet. After calling this method, you will need to call walletpassphrase again before being able to call any methods which require the wallet to be unlocked.
	void walletlock(); //?
	//rounded to the nearest 0.00000001
	boolean settxfee(BigDecimal fee);
	//Sets the account associated with the given address. Assigning address that is already assigned to the same account will create a new address associated with that account.
	boolean setaccount(String bitcoinAddress, String accountLabel);
	//Returns the account associated with the given address.
	String getaccount(String bitcoinAddress);
	//Returns the current bitcoin address for receiving payments to this account.
	String getaccountaddress(String accountLabel);
	//Returns the list of addresses for the given account.
	List<String> getaddressesbyaccount(String accountLabel);
	//If [account] is not specified, returns the server's total available balance.
	BigDecimal getbalance();
	//If [account] is specified, returns the balance in the account.
	BigDecimal getbalance(String account);

	BigDecimal getbalance(String account, int minimumConfirmations);
	//Returns information about the block with the given hash.
	Block getblock(String blockHash);
	//Returns information about the block with the given hash.
	BlockVerbose getblock(String blockHash, int verbosity);
	//Returns the number of blocks in the longest block chain.
	int getblockcount();
	//Returns hash of block in best-block-chain at <index>; index 0 is the genesis block
	String getblockhash(long blockHeight);
	//Returns the number of connections to other nodes.
	int getconnectioncount();
	//Returns the proof-of-work difficulty as a multiple of the minimum difficulty.
	BigDecimal getdifficulty();
	//Returns true or false whether bitcoind is currently generating hashes
	boolean getgenerate();
	//Returns a recent hashes per second performance measurement while generating.
	long gethashespersec();

	//Returns an object about the given transaction hash.
	Transaction gettransaction(String hash);

	//creates a raw transaction and returns its byte representations
	String createrawtransaction(Collection<RawTxInput> rawTxInput, RawTxOutput rawTxOutput);
	//creates a raw transaction and returns its byte representations
	String sendrawtransaction(String rawTransaction);
	
	//listunspent ( minconf maxconf  ["addresses",...] [include_unsafe] [query_options])
	// Returns array of unspent transaction outputs
	List<ListUnspentTO> listunspent(int minconf, int maxconf, List<String> addresses, boolean includeUnsafe, Map<String, String> queryOptions);

	//Returns an object about the given transaction hash.
	String getrawtransaction(String hash);
	//Returns an object about the given transaction hash.
	RawTransaction getrawtransaction(String hash, boolean verbose);
	
	//Returns an object about the given transaction hash.
	RawTransaction decoderawtransaction(String transactonScript);
	//Returns Object that has account names as keys, account balances as values.
	Map<String, BigDecimal> listaccounts(long confirmations);
	//Returns an array of objects containing:"account" : the account of the receiving addresses,"amount" : total amount received by addresses with this account,"confirmations" : number of confirmations of the most recent transaction included
	List<Account> listreceivedbyaccount(long minConfirmations, boolean includeEmpty);
	//Returns an array of objects containing:"address" : receiving address,"account" : the account of the receiving address,"amount" : total amount received by the address,"confirmations" : number of confirmations of the most recent transaction included,To get a list of accounts on the system, execute bitcoind listreceivedbyaddress 0 true
	List<Address> listreceivedbyaddress(long minConfirmations, boolean includeEmpty);
	//Get all transactions in blocks since block [blockhash], or all transactions if omitted.
	List<LastBlock> listsinceblock(String blockhash, int minConfirmations);
	//Returns up to [count] most recent transactions skipping the first [from] transactions for account [account]. If [account] not provided will return recent transaction from all accounts.
	List<Transaction> listtransactions(String account, int count, int offset);
	// Import a private key into your bitcoin wallet. Private key must be in wallet import format (Sipa) beginning with a '5'.
	boolean importprivkey(String privateKey);
	//Move funds from one account in your wallet to another.
	boolean move(String fromAccount, String toAccount, BigDecimal amount);

	boolean move(String fromAccount, String toAccount, BigDecimal amount, long minconf, String comment);
	//amount is a real and is rounded to 8 decimal places. Will send the given amount to the given address, ensuring the account has a valid balance using [minconf] confirmations. Returns the transaction ID if successful (not in JSON object).
	String sendfrom(String fromAccount, String bitcoinAddress, BigDecimal amount);

	String sendfrom(String fromAccount, String bitcoinAddress, BigDecimal amount, long minconf, String comment, String commentTo);
	//amounts are BigDecimal-precision floating point numbers.
	String sendmany(String fromAccount, Map<String, BigDecimal> addressAmountPairs);
	//amounts are BigDecimal-precision floating point numbers.
	String sendmany(String fromAccount, Map<String, BigDecimal> addressAmountPairs, int minconf, String comment);
	//amount is a real and is rounded to 8 decimal places. Returns the transaction hash if successful.
	String sendtoaddress(String bitcoinAddress, BigDecimal amount);

    String sendtoaddress(String bitcoinAddress, BigDecimal amount, String comment, String commentTo, boolean subtractFeeFromAmount, boolean replaceable, int confTarget);

	void setgenerate(boolean generate);

	void setgenerate(boolean generate, int genproclimit);
	// Return information about bitcoinaddress.
	AddressInformation validateaddress(String bitcoinAddress);
	//Returns a new bitcoin address for receiving payments. If [account] is specified (recommended), it is added to the address book so payments received with the address will be credited to [account].
	String getnewaddress(String label);
	//Returns a Base64 encoded signature used to verify the provided message was signed by the owner of bitcoinaddress
	String signmessage(String bitcoinaddress, String message);
	//Verifies the signature and message matches the bitcoin address provided (See signmessage)
	boolean verifymessage(String bitcoinaddress, String signature, String message);
	//stop
	String stop();

	CreateAddress createmultisig(int numberOfSignatures, List<String> addresses);

	FeeDTO estimatesmartfee(int blockNumber);
	
	//Get the mempool transactions
	List<String> getrawmempool();
	
	DecodedScript decodescript(String hexScript);

	void importaddress(String address, String label, boolean rescan, boolean p2sh);
}
