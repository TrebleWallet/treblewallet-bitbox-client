package treblewallet.bitbox.main.java.pojo;

/*
 * Created by Robert Juhas on 7/24/2018
 */
public class PubKeyDTO {
    private String xpub;
    private String echo;

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

	@Override
	public String toString() {
		return "PubKeyDTO [xpub=" + xpub + ", echo=" + echo + "]";
	}

}
