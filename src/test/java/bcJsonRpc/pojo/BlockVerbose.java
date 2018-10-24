package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown=true)
public class BlockVerbose {

	private List<RawTransaction> txs;
	private long time;
	private int height;
	private long nonce;
	private String hash;
	private String bits;
	private long difficulty;
	private String merkleroot;
	private String previousblockhash;
	private String nextblockhash;
	private long confirmations;
	private long version;
	private long size;
	
	private long strippedsize;
	private long weight;
	private String versionHex;
	private long mediantime;
	private String chainwork;
	
	public List<RawTransaction> getTx() {
		return txs;
	}
	public void setTx(List<RawTransaction> tx) {
		this.txs = tx;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public long getNonce() {
		return nonce;
	}
	public void setNonce(long nonce) {
		this.nonce = nonce;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getBits() {
		return bits;
	}
	public void setBits(String bits) {
		this.bits = bits;
	}
	public long getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(long difficulty) {
		this.difficulty = difficulty;
	}
	public String getMerkleroot() {
		return merkleroot;
	}
	public void setMerkleroot(String merkleroot) {
		this.merkleroot = merkleroot;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}
	public String getPreviousblockhash() {
		return previousblockhash;
	}
	public void setPreviousblockhash(String previousblockhash) {
		this.previousblockhash = previousblockhash;
	}
	public String getNextblockhash() {
		return nextblockhash;
	}
	public void setNextblockhash(String nextblockhash) {
		this.nextblockhash = nextblockhash;
	}
	
	public long getStrippedsize() {
		return strippedsize;
	}
	public void setStrippedsize(long strippedsize) {
		this.strippedsize = strippedsize;
	}
	public long getWeight() {
		return weight;
	}
	public void setWeight(long weight) {
		this.weight = weight;
	}
	public String getVersionHex() {
		return versionHex;
	}
	public void setVersionHex(String versionHex) {
		this.versionHex = versionHex;
	}
	public long getMediantime() {
		return mediantime;
	}
	public void setMediantime(long mediantime) {
		this.mediantime = mediantime;
	}
	public String getChainwork() {
		return chainwork;
	}
	public void setChainwork(String chainwork) {
		this.chainwork = chainwork;
	}
	@Override
	public String toString() {
		return "Block [\n"
				+ "hash = "+hash+",\n"
				+ "tx=" + txs +",\n"
				+ "time=" + time +",\n"
				+ "height=" + height +",\n"
				+ "nonce=" + nonce +",\n"
				+ "hash=" + hash+",\n"
				+ "bits=" + bits +",\n"
				+ "difficulty=" + difficulty+",\n" 
				+ "merkleroot=" + merkleroot+",\n"
				+ "previousblockhash=" + previousblockhash+",\n" 
				+ "nextblockhash=" + nextblockhash +",\n"
				+ "confirmations=" + confirmations +",\n"
				+ "version=" + version +",\n"
				+ "size=" + size +",\n"
				+ "strippedsize=" + strippedsize +",\n"
				+ "weight=" + weight +",\n"
				+ "versionHex=" + versionHex +",\n"
				+ "mediantime=" + mediantime +",\n"
				+ "chainwork=" + chainwork +",\n"
				+ "]";
	}
}
