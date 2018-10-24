package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown=true)
public class BIP9SoftForks {
    private Segwit segwit;
    private CSV csv;
	public Segwit getSegwit() {
		return segwit;
	}
	public void setSegwit(Segwit segwit) {
		this.segwit = segwit;
	}
	public CSV getCsv() {
		return csv;
	}
	public void setCsv(CSV csv) {
		this.csv = csv;
	}
	@Override
	public String toString() {
		return "BIP9SoftForks []";
	}
}
