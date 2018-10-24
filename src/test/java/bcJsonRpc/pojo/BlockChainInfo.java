package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown=true)
public class BlockChainInfo {
	private String chain;
	private long blocks;
	private long headers;
	private String bestblockhash;
	private BigDecimal difficulty;
	private long mediantime;
	private String chainwork;
	private long size_on_disk;
	private boolean pruned;
	private double verificationprogress;
	private boolean initialblockdownload;
	private List<SoftForks> softforks;
	private BIP9SoftForks bip9_softforks;
	private String warnings;
	public String getChain() {
		return chain;
	}
	public void setChain(String chain) {
		this.chain = chain;
	}
	public long getBlocks() {
		return blocks;
	}
	public void setBlocks(long blocks) {
		this.blocks = blocks;
	}
	public long getHeaders() {
		return headers;
	}
	public void setHeaders(long headers) {
		this.headers = headers;
	}
	public String getBestblockhash() {
		return bestblockhash;
	}
	public void setBestblockhash(String bestblockhash) {
		this.bestblockhash = bestblockhash;
	}
	public BigDecimal getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(BigDecimal difficulty) {
		this.difficulty = difficulty;
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
	public long getSize_on_disk() {
		return size_on_disk;
	}
	public void setSize_on_disk(long size_on_disk) {
		this.size_on_disk = size_on_disk;
	}
	public boolean isPruned() {
		return pruned;
	}
	public void setPruned(boolean pruned) {
		this.pruned = pruned;
	}
	public double getVerificationprogress() {
		return verificationprogress;
	}
	public void setVerificationprogress(double verificationprogress) {
		this.verificationprogress = verificationprogress;
	}
	public boolean isInitialblockdownload() {
		return initialblockdownload;
	}
	public void setInitialblockdownload(boolean initialblockdownload) {
		this.initialblockdownload = initialblockdownload;
	}
	public List<SoftForks> getSoftforks() {
		return softforks;
	}
	public void setSoftforks(List<SoftForks> softforks) {
		this.softforks = softforks;
	}
	public BIP9SoftForks getBip9_softforks() {
		return bip9_softforks;
	}
	public void setBip9_softforks(BIP9SoftForks bip9_softforks) {
		this.bip9_softforks = bip9_softforks;
	}
	public String getWarnings() {
		return warnings;
	}
	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}
	@Override
	public String toString() {
		return "BlockChainInfo [chain=" + chain + ", blocks=" + blocks + ", headers=" + headers + ", bestblockhash="
				+ bestblockhash + ", difficulty=" + difficulty + ", mediantime=" + mediantime + ", chainwork="
				+ chainwork + ", pruned=" + pruned + ", verificationprogress=" + verificationprogress + ", softforks="
				+ softforks + ", bip9_softforks=" + bip9_softforks + "]";
	}
}
