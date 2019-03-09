package api.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String login;
	private String email;
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	private String description;
	private Date dateOfBirth;
	private Date createdAt;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="homeAddressId")
	private Location homeAddress;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Offer> offers;
	
	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Application> applications;
	
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comment> receivedComments;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comment> postedComments;

	public User(String login, String email, String password, String firstName, String lastName, String description, Date dateOfBirth) {
		this.login = login;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth= dateOfBirth;
		this.description = description;
		this.createdAt = new Date();
		homeAddress = new Location();
		offers = new ArrayList<Offer>();
	}
	
	public User() {
		login = "";
		email = "";
		password = "";
		description = "";
		homeAddress = new Location();
		offers = new ArrayList<Offer>();
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Location getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Location homeAddress) {
		this.homeAddress = homeAddress;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(ArrayList<Offer> offers) {
		this.offers = offers;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	public List<Comment> getReceivedComments() {
		return receivedComments;
	}

	public void setReceivedComments(List<Comment> receivedComments) {
		this.receivedComments = receivedComments;
	}

	public List<Comment> getPostedComments() {
		return postedComments;
	}

	public void setPostedComments(List<Comment> postedComments) {
		this.postedComments = postedComments;
	}
}
