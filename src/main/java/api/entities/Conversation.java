package api.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "usera_id", "userb_id" }) })
public class Conversation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "email", "description", "dateOfBirth", "createdAt", "homeAddress" })
	private User userA;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "email", "description", "dateOfBirth", "createdAt", "homeAddress" })
	private User userB;

	@OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
	private List<Message> messages;
	private Date seenAt;

	public Conversation() {
		super();
		setUserA(null);
		setUserB(null);
		setMessages(new ArrayList<Message>());
		setSeenAt(null);
	}

	public Conversation(User userA, User userB) {
		setUserA(userA);
		setUserB(userB);
		setMessages(new ArrayList<Message>());
		setSeenAt(new Date());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUserA() {
		return userA;
	}

	public void setUserA(User userA) {
		this.userA = userA;
	}

	public User getUserB() {
		return userB;
	}

	public void setUserB(User userB) {
		this.userB = userB;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Date getSeenAt() {
		return seenAt;
	}

	public void setSeenAt(Date seenAt) {
		this.seenAt = seenAt;
	}

	@Override
	public String toString() {
		return "Conversation [id=" + id + ", userA=" + userA + ", userB=" + userB + ", messages=" + messages
				+ ", seenAt=" + seenAt + "]";
	}
}
