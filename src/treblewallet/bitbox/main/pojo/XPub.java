package treblewallet.bitbox.main.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class XPub {
	private String xpub;
	private String echo;
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
		return "XPub [xpub=" + xpub + ", echo=" + echo + "]";
	}
}
