package pojo;

/*
 * Created by Robert Juhas on 7/24/2018
 */
public class PubKeyPathDTO {
	private String pubkey;
	private String path;

	public PubKeyPathDTO() {
	}

	public String getPubkey() {
		return pubkey;
	}

	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "PubKeyPathDTO [pubkey=" + pubkey + ", path=" + path + "]";
	}

}
