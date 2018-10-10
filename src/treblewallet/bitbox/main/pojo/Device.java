package treblewallet.bitbox.main.pojo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Device {

	private String serial;
	private Boolean U2FHijack;
	private String name;
	private Boolean U2F;
	private Boolean lock;
	private String id;
	private Boolean bootlock;
	private Boolean seeded;
	private String version;
	private Boolean sdcard;
	private String TFA;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Boolean getU2FHijack() {
		return U2FHijack;
	}

	public void setU2FHijack(Boolean u2FHijack) {
		this.U2FHijack = u2FHijack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getU2F() {
		return U2F;
	}

	public void setU2F(Boolean u2F) {
		this.U2F = u2F;
	}

	public Boolean getLock() {
		return lock;
	}

	public void setLock(Boolean lock) {
		this.lock = lock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getBootlock() {
		return bootlock;
	}

	public void setBootlock(Boolean bootlock) {
		this.bootlock = bootlock;
	}

	public Boolean getSeeded() {
		return seeded;
	}

	public void setSeeded(Boolean seeded) {
		this.seeded = seeded;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Boolean getSdcard() {
		return sdcard;
	}

	public void setSdcard(Boolean sdcard) {
		this.sdcard = sdcard;
	}

	public String getTFA() {
		return TFA;
	}

	public void setTFA(String tFA) {
		this.TFA = tFA;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Device [serial=" + serial + ", U2FHijack=" + U2FHijack + ", name=" + name + ", U2F=" + U2F + ", lock="
				+ lock + ", id=" + id + ", bootlock=" + bootlock + ", seeded=" + seeded + ", version=" + version
				+ ", sdcard=" + sdcard + ", TFA=" + TFA + ", additionalProperties=" + additionalProperties + "]";
	}

}