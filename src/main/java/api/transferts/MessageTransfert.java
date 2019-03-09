package api.transferts;

public class MessageTransfert {
	private String message;
	private int senderId;
	private int receiverId;

	public MessageTransfert() {
		setMessage(null);
		setSenderId(-1);
		setReceiverId(-1);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	@Override
	public String toString() {
		return "MessageTransfert [message=" + message + ", senderId=" + senderId + ", receiverId=" + receiverId + "]";
	}
}
