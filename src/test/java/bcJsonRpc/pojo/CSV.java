package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown=true)
public class CSV {
    private String status;
    private long startTime;
    private long timeout;
    private long since;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public long getSince() {
		return since;
	}
	public void setSince(long since) {
		this.since = since;
	}
	@Override
	public String toString() {
		return "CSV [status=" + status + ", startTime=" + startTime + ", timeout=" + timeout + ", since=" + since + "]";
	}
}
