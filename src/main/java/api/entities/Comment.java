package api.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String message;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "email", "description", "dateOfBirth", "createdAt", "homeAddress" })
	private User sender;
	
	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "email", "description", "dateOfBirth", "createdAt", "homeAddress" })
	private User receiver;
	
	private Integer note;
	
	private Date createdAt;


	public Comment() {
		super();
	}

	public Comment(User sender, User receiver, String message, Integer note) {
		super();
		setMessage(message);
		setSender(sender);
		setReceiver(receiver);
		setNote(note);
		setCreatedAt(new Date());
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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
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

}
