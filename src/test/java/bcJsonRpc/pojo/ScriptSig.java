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
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "asm", "hex" })
public class ScriptSig {

	@JsonProperty("asm")
	private String asm;
	@JsonProperty("hex")
	private String hex;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("asm")
	public String getAsm() {
		return asm;
	}

	@JsonProperty("asm")
	public void setAsm(String asm) {
		this.asm = asm;
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
		return new ToStringBuilder(this).append("asm", asm).append("hex", hex)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(hex).append(additionalProperties).append(asm).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ScriptSig) == false) {
			return false;
		}
		ScriptSig rhs = ((ScriptSig) other);
		return new EqualsBuilder().append(hex, rhs.hex).append(additionalProperties, rhs.additionalProperties)
				.append(asm, rhs.asm).isEquals();
	}
}