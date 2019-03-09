package api.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String message;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "email", "description", "dateOfBirth", "createdAt", "homeAddress" })
	private User sender;
	private Date sendAt;

	@ManyToOne(optional = false)
	@JsonIgnore
	private Conversation conversation;

	public Message() {
		super();
		setMessage(null);
		setSender(null);
		setSendAt(new Date());
		setConversation(null);
	}

	public Message(User sender, String message, Conversation conversation) {
		super();
		setMessage(message);
		setSender(sender);
		setSendAt(new Date());
		setConversation(conversation);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Date getSendAt() {
		return sendAt;
	}

	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", sender=" + sender + ", sendAt=" + sendAt + "]";
	}
}
