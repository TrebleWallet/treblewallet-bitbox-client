package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown=true)
public class SoftForks {
    private String id;
    private Reject reject;
    private int version;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Reject getReject() {
		return reject;
	}
	public void setReject(Reject reject) {
		this.reject = reject;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "SoftForks [id=" + id + ", version=" + version + "]";
	}
    
}
