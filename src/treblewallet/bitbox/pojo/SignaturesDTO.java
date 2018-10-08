package treblewallet.bitbox.pojo;

public class SignaturesDTO {
	String sig;
	String recid;
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
