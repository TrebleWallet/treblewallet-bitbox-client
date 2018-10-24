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
@JsonPropertyOrder({ "txid", "vout", "scriptSig", "sequence" })
public class Vin {

	@JsonProperty("txid")
	private String txid;
	@JsonProperty("vout")
	private Integer vout;
	@JsonProperty("scriptSig")
	private ScriptSig scriptSig;
	@JsonProperty("sequence")
	private Integer sequence;
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

	@JsonProperty("vout")
	public Integer getVout() {
		return vout;
	}

	@JsonProperty("vout")
	public void setVout(Integer vout) {
		this.vout = vout;
	}

	@JsonProperty("scriptSig")
	public ScriptSig getScriptSig() {
		return scriptSig;
	}

	@JsonProperty("scriptSig")
	public void setScriptSig(ScriptSig scriptSig) {
		this.scriptSig = scriptSig;
	}

	@JsonProperty("sequence")
	public Integer getSequence() {
		return sequence;
	}

	@JsonProperty("sequence")
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("txid", txid).append("vout", vout).append("scriptSig", scriptSig)
				.append("sequence", sequence).append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(additionalProperties).append(sequence).append(vout).append(txid)
				.append(scriptSig).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Vin) == false) {
			return false;
		}
		Vin rhs = ((Vin) other);
		return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(sequence, rhs.sequence)
				.append(vout, rhs.vout).append(txid, rhs.txid).append(scriptSig, rhs.scriptSig).isEquals();
	}
}