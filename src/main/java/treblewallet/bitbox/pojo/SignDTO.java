package treblewallet.bitbox.pojo;

import java.util.Arrays;

/*
 * Created by Robert Juhas on 7/24/2018
 */
public class SignDTO {
	private String meta;
	private HashKeyPathDTO[] data;
	private PubKeyPathDTO[] checkpub;
	private String echo;
	
	private String recid;
	private SignaturesDTO[] sign;
	
	private ErrorDTO error;

	public SignDTO() {
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public String getEcho() {
		return echo;
	}

	public void setEcho(String echo) {
		this.echo = echo;
	}

	public HashKeyPathDTO[] getData() {
		return data;
	}

	public void setData(HashKeyPathDTO[] data) {
		this.data = data;
	}

	public PubKeyPathDTO[] getCheckpub() {
		return checkpub;
	}

	public void setCheckpub(PubKeyPathDTO[] checkpub) {
		this.checkpub = checkpub;
	}

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}


	public SignaturesDTO[] getSign() {
		return sign;
	}

	public void setSign(SignaturesDTO[] sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "SignDTO [meta=" + meta + ", data=" + Arrays.toString(data) + ", checkpub=" + Arrays.toString(checkpub)
				+ ", echo=" + echo + ", recid=" + recid + ", sign=" + Arrays.toString(sign) + ", error=" + error + "]";
	}

	public ErrorDTO getError() {
		return error;
	}

	public void setError(ErrorDTO error) {
		this.error = error;
	}
}
