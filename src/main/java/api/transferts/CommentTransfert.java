package api.transferts;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class CommentTransfert {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String message;

	private long senderId;
	
	private long receiverId;
	
	private Integer note;
	
	private Date createdAt;


	public CommentTransfert() {
		super();
	}

	public CommentTransfert(long senderId, long receiverId, String message, Integer note) {
		super();
		setMessage(message);
		setSenderId(senderId);
		setReceiverId(receiverId);
		setNote(note);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

}
