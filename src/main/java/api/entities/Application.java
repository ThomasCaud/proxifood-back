package api.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "applicant_id", "offer_id" }) })
public class Application {
	public enum EnumStatut {
		PENDING, ACCEPTED, CANCELED;
	}

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "email", "firstName", "lastName", "description", "dateOfBirth", "createdAt", "homeAddress",
			"offers" })
	private User applicant;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "allowHelpCooking", "endOfInscription", "createdAt", "description", "price" })
	private Offer offer;

	private Date askedAt;
	private int nbPlaces;
	private boolean helpCooking;
	@Enumerated(EnumType.STRING)
	public EnumStatut statut;

	public Application() {
		super();
		setApplicant(null);
		setOffer(null);
		setAskedAt(new Date());
		setNbPlaces(-1);
		setHelpCooking(false);
		setStatut(EnumStatut.PENDING);
	}

	public Application(User applicant, Offer offer, int nbPlaces, boolean helpCooking) {
		setApplicant(applicant);
		setOffer(offer);
		setAskedAt(new Date());
		setNbPlaces(nbPlaces);
		setHelpCooking(helpCooking);
		setStatut(EnumStatut.PENDING);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Date getAskedAt() {
		return askedAt;
	}

	public void setAskedAt(Date askedAt) {
		this.askedAt = askedAt;
	}

	public int getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}

	public boolean isHelpCooking() {
		return helpCooking;
	}

	public void setHelpCooking(boolean helpCooking) {
		this.helpCooking = helpCooking;
	}

	public EnumStatut getStatut() {
		return statut;
	}

	public void setStatut(EnumStatut statut) {
		this.statut = statut;
	}
}
