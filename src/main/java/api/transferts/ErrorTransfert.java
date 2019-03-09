package api.transferts;

public class ErrorTransfert {
	private String message;
	private int code;

	public ErrorTransfert() {
		setMessage("");
		setCode(0);
	}

	public ErrorTransfert(String message) {
		setMessage(message);
		setCode(400);
	}

	public ErrorTransfert(String message, int code) {
		setMessage(message);
		setCode(code);
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

	@Override
	public String toString() {
		return super.toString();
	}
}
