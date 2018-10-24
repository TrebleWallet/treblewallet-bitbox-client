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
@JsonPropertyOrder({ "account", "address", "category", "amount", "label", "vout", "fee", "abandoned" })
public class Detail {

	@JsonProperty("account")
	private String account;
	@JsonProperty("address")
	private String address;
	@JsonProperty("category")
	private String category;
	@JsonProperty("amount")
	private Double amount;
	@JsonProperty("label")
	private String label;
	@JsonProperty("vout")
	private Integer vout;
	@JsonProperty("fee")
	private Double fee;
	@JsonProperty("abandoned")
	private Boolean abandoned;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("account")
	public String getAccount() {
		return account;
	}

	@JsonProperty("account")
	public void setAccount(String account) {
		this.account = account;
	}

	@JsonProperty("address")
	public String getAddress() {
		return address;
	}

	@JsonProperty("address")
	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty("category")
	public String getCategory() {
		return category;
	}

	@JsonProperty("category")
	public void setCategory(String category) {
		this.category = category;
	}

	@JsonProperty("amount")
	public Double getAmount() {
		return amount;
	}

	@JsonProperty("amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@JsonProperty("label")
	public String getLabel() {
		return label;
	}

	@JsonProperty("label")
	public void setLabel(String label) {
		this.label = label;
	}

	@JsonProperty("vout")
	public Integer getVout() {
		return vout;
	}

	@JsonProperty("vout")
	public void setVout(Integer vout) {
		this.vout = vout;
	}

	@JsonProperty("fee")
	public Double getFee() {
		return fee;
	}

	@JsonProperty("fee")
	public void setFee(Double fee) {
		this.fee = fee;
	}

	@JsonProperty("abandoned")
	public Boolean getAbandoned() {
		return abandoned;
	}

	@JsonProperty("abandoned")
	public void setAbandoned(Boolean abandoned) {
		this.abandoned = abandoned;
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
		return new ToStringBuilder(this).append("account", account).append("address", address)
				.append("category", category).append("amount", amount).append("label", label).append("vout", vout)
				.append("fee", fee).append("abandoned", abandoned).append("additionalProperties", additionalProperties)
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(fee).append(amount).append(category).append(address)
				.append(additionalProperties).append(vout).append(account).append(label).append(abandoned).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Detail) == false) {
			return false;
		}
		Detail rhs = ((Detail) other);
		return new EqualsBuilder().append(fee, rhs.fee).append(amount, rhs.amount).append(category, rhs.category)
				.append(address, rhs.address).append(additionalProperties, rhs.additionalProperties)
				.append(vout, rhs.vout).append(account, rhs.account).append(label, rhs.label)
				.append(abandoned, rhs.abandoned).isEquals();
	}
}