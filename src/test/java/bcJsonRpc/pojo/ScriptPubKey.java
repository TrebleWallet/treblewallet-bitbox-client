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
@JsonPropertyOrder({ "asm", "hex", "reqSigs", "type", "addresses" })
public class ScriptPubKey {

	@JsonProperty("asm")
	private String asm;
	@JsonProperty("hex")
	private String hex;
	@JsonProperty("reqSigs")
	private Integer reqSigs;
	@JsonProperty("type")
	private String type;
	@JsonProperty("addresses")
	private List<String> addresses = null;
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

	@JsonProperty("reqSigs")
	public Integer getReqSigs() {
		return reqSigs;
	}

	@JsonProperty("reqSigs")
	public void setReqSigs(Integer reqSigs) {
		this.reqSigs = reqSigs;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("addresses")
	public List<String> getAddresses() {
		return addresses;
	}

	@JsonProperty("addresses")
	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
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
		return new ToStringBuilder(this).append("asm", asm).append("hex", hex).append("reqSigs", reqSigs)
				.append("type", type).append("addresses", addresses)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(hex).append(reqSigs).append(additionalProperties).append(addresses)
				.append(type).append(asm).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ScriptPubKey) == false) {
			return false;
		}
		ScriptPubKey rhs = ((ScriptPubKey) other);
		return new EqualsBuilder().append(hex, rhs.hex).append(reqSigs, rhs.reqSigs)
				.append(additionalProperties, rhs.additionalProperties).append(addresses, rhs.addresses)
				.append(type, rhs.type).append(asm, rhs.asm).isEquals();
	}
}