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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "value", "n", "scriptPubKey" })
public class Vout {

	@JsonProperty("value")
	private BigDecimal value;
	@JsonProperty("n")
	private Integer n;
	@JsonProperty("scriptPubKey")
	private ScriptPubKey scriptPubKey;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("value")
	public BigDecimal getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@JsonProperty("n")
	public Integer getN() {
		return n;
	}

	@JsonProperty("n")
	public void setN(Integer n) {
		this.n = n;
	}

	@JsonProperty("scriptPubKey")
	public ScriptPubKey getScriptPubKey() {
		return scriptPubKey;
	}

	@JsonProperty("scriptPubKey")
	public void setScriptPubKey(ScriptPubKey scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("value", value).append("n", n).append("scriptPubKey", scriptPubKey)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(additionalProperties).append(scriptPubKey).append(n).append(value)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Vout) == false) {
			return false;
		}
		Vout rhs = ((Vout) other);
		return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties)
				.append(scriptPubKey, rhs.scriptPubKey).append(n, rhs.n).append(value, rhs.value).isEquals();
	}
}