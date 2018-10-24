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
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "value", "n", "scriptPubKey" })
public class RawTxInput {

	@JsonProperty("txid")
	private String txid;
	@JsonProperty("vout")
	private Integer vout;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public RawTxInput(String txid, Integer vout, Map<String, Object> additionalProperties) {
		super();
		this.txid = txid;
		this.vout = vout;
		this.additionalProperties = additionalProperties;
	}

	@JsonProperty("txid")
	public String getTxid() {
		return txid;
	}

	@JsonProperty("txid")
	public void setTxid(String txid) {
		this.txid = txid;
	}

	@JsonProperty("vout")
	public Integer getVout() {
		return vout;
	}

	@JsonProperty("vout")
	public void setVout(Integer vout) {
		this.vout = vout;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("txid", txid).append("vout", vout)
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(additionalProperties).append(txid).append(vout).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof RawTxInput) == false) {
			return false;
		}
		RawTxInput rhs = ((RawTxInput) other);
		return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(txid, rhs.txid)
				.append(vout, rhs.vout).isEquals();
	}

}