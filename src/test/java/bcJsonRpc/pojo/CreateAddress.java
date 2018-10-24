package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
* Created by Robert Juhas on 3/27/2018
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAddress {
    @JsonProperty("address")
    private String address;

    @JsonProperty("redeemScript")
    private String redeemScript;

    public CreateAddress() {
    }

    public CreateAddress(String address, String redeemScript) {
        this.address = address;
        this.redeemScript = redeemScript;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRedeemScript() {
        return redeemScript;
    }

    public void setRedeemScript(String redeemScript) {
        this.redeemScript = redeemScript;
    }
}
