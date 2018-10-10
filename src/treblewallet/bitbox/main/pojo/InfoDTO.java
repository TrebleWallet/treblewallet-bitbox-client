package treblewallet.bitbox.main.pojo;

import java.util.HashMap;
import java.util.Map;

/*
 * Created by Robert Juhas on 7/24/2018
 */
public class InfoDTO {
	private Device device;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "InfoDTO [device=" + device + ", additionalProperties=" + additionalProperties + "]";
	}
	
}
