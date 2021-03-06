package treblewallet.bitbox.pojo;

/*
 * Created by Robert Juhas on 7/24/2018
 */
public class PubKeyDTO {
    private String xpub;
    private String echo;
    private ErrorDTO error;

    public PubKeyDTO() {
    }

    public String getXpub() {
        return xpub;
    }

    public void setXpub(String xpub) {
        this.xpub = xpub;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }

    @Override
	public String toString() {
		return "PubKeyDTO [xpub=" + xpub + ", echo=" + echo + "]";
	}

}
