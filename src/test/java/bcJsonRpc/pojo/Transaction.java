package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "amount", "fee", "confirmations", "blockhash", "blockindex", "blocktime", "txid",
		"walletconflicts", "time", "timereceived", "bip125-replaceable", "details", "hex" })
public class Transaction {

	@JsonProperty("amount")
	private Double amount;
	@JsonProperty("fee")
	private Double fee;
	@JsonProperty("confirmations")
	private Integer confirmations;
	@JsonProperty("blockhash")
	private String blockhash;
	@JsonProperty("blockindex")
	private Integer blockindex;
	@JsonProperty("blocktime")
	private Integer blocktime;
	@JsonProperty("txid")
	private String txid;
	@JsonProperty("walletconflicts")
	private List<Object> walletconflicts = null;
	@JsonProperty("time")
	private Integer time;
	@JsonProperty("timereceived")
	private Integer timereceived;
	@JsonProperty("bip125-replaceable")
	private String bip125Replaceable;
	@JsonProperty("details")
	private List<Detail> details = null;
	@JsonProperty("hex")
	private String hex;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("amount")
	public Double getAmount() {
		return amount;
	}

	@JsonProperty("amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@JsonProperty("fee")
	public Double getFee() {
		return fee;
	}

	@JsonProperty("fee")
	public void setFee(Double fee) {
		this.fee = fee;
	}

	@JsonProperty("confirmations")
	public Integer getConfirmations() {
		return confirmations;
	}

	@JsonProperty("confirmations")
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}

	@JsonProperty("blockhash")
	public String getBlockhash() {
		return blockhash;
	}

	@JsonProperty("blockhash")
	public void setBlockhash(String blockhash) {
		this.blockhash = blockhash;
	}

	@JsonProperty("blockindex")
	public Integer getBlockindex() {
		return blockindex;
	}

	@JsonProperty("blockindex")
	public void setBlockindex(Integer blockindex) {
		this.blockindex = blockindex;
	}

	@JsonProperty("blocktime")
	public Integer getBlocktime() {
		return blocktime;
	}

	@JsonProperty("blocktime")
	public void setBlocktime(Integer blocktime) {
		this.blocktime = blocktime;
	}

	@JsonProperty("txid")
	public String getTxid() {
		return txid;
	}

	@JsonProperty("txid")
	public void setTxid(String txid) {
		this.txid = txid;
	}

	@JsonProperty("walletconflicts")
	public List<Object> getWalletconflicts() {
		return walletconflicts;
	}

	@JsonProperty("walletconflicts")
	public void setWalletconflicts(List<Object> walletconflicts) {
		this.walletconflicts = walletconflicts;
	}

	@JsonProperty("time")
	public Integer getTime() {
		return time;
	}

	@JsonProperty("time")
	public void setTime(Integer time) {
		this.time = time;
	}

	@JsonProperty("timereceived")
	public Integer getTimereceived() {
		return timereceived;
	}

	@JsonProperty("timereceived")
	public void setTimereceived(Integer timereceived) {
		this.timereceived = timereceived;
	}

	@JsonProperty("bip125-replaceable")
	public String getBip125Replaceable() {
		return bip125Replaceable;
	}

	@JsonProperty("bip125-replaceable")
	public void setBip125Replaceable(String bip125Replaceable) {
		this.bip125Replaceable = bip125Replaceable;
	}

	@JsonProperty("details")
	public List<Detail> getDetails() {
		return details;
	}

	@JsonProperty("details")
	public void setDetails(List<Detail> details) {
		this.details = details;
	}

	@JsonProperty("hex")
	public String getHex() {
		return hex;
	}

	@JsonProperty("hex")
	public void setHex(String hex) {
		this.hex = hex;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("amount", amount).append("fee", fee)
				.append("confirmations", confirmations).append("blockhash", blockhash).append("blockindex", blockindex)
				.append("blocktime", blocktime).append("txid", txid).append("walletconflicts", walletconflicts)
				.append("time", time).append("timereceived", timereceived)
				.append("bip125Replaceable", bip125Replaceable).append("details", details).append("hex", hex)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(confirmations).append(blockhash).append(blocktime).append(fee)
				.append(amount).append(bip125Replaceable).append(blockindex).append(time).append(hex).append(details)
				.append(timereceived).append(additionalProperties).append(txid).append(walletconflicts).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Transaction) == false) {
			return false;
		}
		Transaction rhs = ((Transaction) other);
		return new EqualsBuilder().append(confirmations, rhs.confirmations).append(blockhash, rhs.blockhash)
				.append(blocktime, rhs.blocktime).append(fee, rhs.fee).append(amount, rhs.amount)
				.append(bip125Replaceable, rhs.bip125Replaceable).append(blockindex, rhs.blockindex)
				.append(time, rhs.time).append(hex, rhs.hex).append(details, rhs.details)
				.append(timereceived, rhs.timereceived).append(additionalProperties, rhs.additionalProperties)
				.append(txid, rhs.txid).append(walletconflicts, rhs.walletconflicts).isEquals();
	}
}