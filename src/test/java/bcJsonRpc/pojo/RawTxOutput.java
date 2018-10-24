package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawTxOutput {

	private Map<String, Object> addressValuePairs = new HashMap<String, Object>();

	public RawTxOutput(Map<String, Object> addressValuePairs) {
		super();
		this.addressValuePairs = addressValuePairs;
	}

	public RawTxOutput() {
	}

	@JsonAnyGetter
	public Map<String, Object> getAddressValuePairs() {
		return this.addressValuePairs;
	}

	@JsonAnySetter
	public void setAddressValuePairs(String name, Object value) {
		this.addressValuePairs.put(name, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(addressValuePairs).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(addressValuePairs).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof RawTxOutput) == false) {
			return false;
		}
		RawTxOutput rhs = ((RawTxOutput) other);
		return new EqualsBuilder().append(addressValuePairs, rhs.addressValuePairs).isEquals();
	}
}