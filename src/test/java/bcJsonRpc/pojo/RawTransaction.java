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
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "txid", "hash", "version", "size", "vsize", "locktime", "vin", "vout" })
public class RawTransaction {

	@JsonProperty("txid")
	private String txid;
	@JsonProperty("hash")
	private String hash;
	@JsonProperty("version")
	private Integer version;
	@JsonProperty("size")
	private Integer size;
	@JsonProperty("vsize")
	private Integer vsize;
	@JsonProperty("locktime")
	private Integer locktime;
	@JsonProperty("time")
	private Integer time;
	@JsonProperty("vin")
	private List<Vin> vin = null;
	@JsonProperty("vout")
	private List<Vout> vout = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("txid")
	public String getTxid() {
		return txid;
	}

	@JsonProperty("txid")
	public void setTxid(String txid) {
		this.txid = txid;
	}

	@JsonProperty("hash")
	public String getHash() {
		return hash;
	}

	@JsonProperty("hash")
	public void setHash(String hash) {
		this.hash = hash;
	}

	@JsonProperty("version")
	public Integer getVersion() {
		return version;
	}

	@JsonProperty("version")
	public void setVersion(Integer version) {
		this.version = version;
	}

	@JsonProperty("size")
	public Integer getSize() {
		return size;
	}

	@JsonProperty("size")
	public void setSize(Integer size) {
		this.size = size;
	}

	@JsonProperty("vsize")
	public Integer getVsize() {
		return vsize;
	}

	@JsonProperty("vsize")
	public void setVsize(Integer vsize) {
		this.vsize = vsize;
	}

	@JsonProperty("locktime")
	public Integer getLocktime() {
		return locktime;
	}

	@JsonProperty("locktime")
	public void setLocktime(Integer locktime) {
		this.locktime = locktime;
	}

	@JsonProperty("vin")
	public List<Vin> getVin() {
		return vin;
	}

	@JsonProperty("vin")
	public void setVin(List<Vin> vin) {
		this.vin = vin;
	}

	@JsonProperty("vout")
	public List<Vout> getVout() {
		return vout;
	}

	@JsonProperty("vout")
	public void setVout(List<Vout> vout) {
		this.vout = vout;
	}

	@JsonProperty("time")
	public Integer getTime() {
		return time;
	}

	@JsonProperty("time")
	public void setTime(Integer time) {
		this.time = time;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("txid", txid).append("hash", hash)
				.append("version", version).append("size", size).append("vsize", vsize).append("locktime", locktime)
				.append("vin", vin).append("vout", vout).append("time", time)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(hash).append(additionalProperties).append(locktime).append(vin).append(vout)
				.append(time).append(txid).append(vsize).append(size).append(version).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof RawTransaction) == false) {
			return false;
		}
		RawTransaction rhs = ((RawTransaction) other);
		return new EqualsBuilder().append(hash, rhs.hash).append(additionalProperties, rhs.additionalProperties)
				.append(locktime, rhs.locktime).append(vin, rhs.vin).append(vout, rhs.vout).append(time, rhs.time)
				.append(txid, rhs.txid).append(vsize, rhs.vsize).append(size, rhs.size).append(version, rhs.version)
				.isEquals();
	}
}