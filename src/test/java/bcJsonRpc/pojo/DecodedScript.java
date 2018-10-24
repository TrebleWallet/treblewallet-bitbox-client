package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/*
 * Created by Robert Juhas on 7/5/2018
 */
@JsonPropertyOrder({"asm", "hex", "type", "reqSigs", "addresses", "p2sh"})
public class DecodedScript {
    @JsonProperty("asm")
    private String scriptPublicKey;

    @JsonProperty("hex")
    private String publicKeyHex;

    @JsonProperty("type")
    private String outputType;

    @JsonProperty("reqSigs")
    private Long requiredSignatures;

    @JsonProperty("addresses")
    private List<String> addresses;

    @JsonProperty("p2sh")
    private String p2sh;

    public DecodedScript() {
    }

    public String getScriptPublicKey() {
        return scriptPublicKey;
    }

    public void setScriptPublicKey(String scriptPublicKey) {
        this.scriptPublicKey = scriptPublicKey;
    }

    public String getPublicKeyHex() {
        return publicKeyHex;
    }

    public void setPublicKeyHex(String publicKeyHex) {
        this.publicKeyHex = publicKeyHex;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getP2sh() {
        return p2sh;
    }

    public void setP2sh(String p2sh) {
        this.p2sh = p2sh;
    }
}
