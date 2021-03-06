package treblewallet.bitbox.pojo;

public class ErrorDTO {
	private String message;
	private int code;
	private String command;

	public ErrorDTO() {
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	@Override
	public String toString() {
		return "ErrorDTO [message=" + message + ", code=" + code + ", command=" + command + "]";
	}
	
}
