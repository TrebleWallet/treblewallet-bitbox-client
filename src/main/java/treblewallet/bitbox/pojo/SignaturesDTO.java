package treblewallet.bitbox.pojo;

public class SignaturesDTO {
	private String sig;
	private String recid;

	public SignaturesDTO() {
	}

	public String getSig() {
		return sig;
	}
	public void setSig(String sig) {
		this.sig = sig;
	}
	public String getRecid() {
		return recid;
	}
	public void setRecid(String recid) {
		this.recid = recid;
	}
	@Override
	public String toString() {
		return "SignaturesDTO [sig=" + sig + ", recid=" + recid + "]";
	}
	
}
