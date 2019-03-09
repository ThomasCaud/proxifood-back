package api.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import api.entities.Application.EnumStatut;
import api.transferts.OfferTransfert;

@Entity
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "offers", "dateOfBirth", "createdAt" })
	private User creator;
	private boolean allowHelpCooking;
	private Date date;
	private Date endOfInscription;
	private Date createdAt;
	private String meal;
	private String description;
	private Integer nbPlaces;
	private float price;
	private int nbAcceptedPeople;

	@OneToMany(mappedBy = "offer", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Application> applications;

	public Offer() {
		super();
		setCreator(new User());
		setAllowHelpCooking(false);
		setDate(new Date());
		setEndOfInscription(new Date());
		setCreatedAt(new Date());
		setMeal("no meal");
		setDescription("no description");
		setNbPlaces(0);
		setPrice(0);
		this.nbAcceptedPeople = 0;
	}

	public Offer(@Lazy User creator, boolean allowHelpCooking, Date date, Date endOfInscription, String meal,
			String description, Integer nbPlaces, float price) {
		super();
		this.creator = creator;
		this.allowHelpCooking = allowHelpCooking;
		this.date = date;
		this.createdAt = new Date();
		this.endOfInscription = endOfInscription;
		this.meal = meal;
		this.description = description;
		this.nbPlaces = nbPlaces;
		this.price = price;
		this.nbAcceptedPeople = 0;
	}

	public Offer(OfferTransfert off) {
		super();
		this.allowHelpCooking = off.isAllowHelpCooking();
		this.date = off.getDate();
		this.createdAt = new Date();
		this.endOfInscription = off.getEndOfInscription();
		this.meal = off.getMeal();
		this.description = off.getDescription();
		this.nbPlaces = off.getNbPlaces();
		this.price = off.getPrice();
		this.nbAcceptedPeople = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public boolean isAllowHelpCooking() {
		return allowHelpCooking;
	}

	public void setAllowHelpCooking(boolean allowHelpCooking) {
		this.allowHelpCooking = allowHelpCooking;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getEndOfInscription() {
		return endOfInscription;
	}

	public void setEndOfInscription(Date endOfInscription) {
		this.endOfInscription = endOfInscription;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(Integer nbPlaces) {
		this.nbPlaces = nbPlaces;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Offer [id=" + id + ", creator=" + creator + ", allowHelpCooking=" + allowHelpCooking + ", date=" + date
				+ ", endOfInscription=" + endOfInscription + ", createdAt=" + createdAt + ", meal=" + meal
				+ ", description=" + description + ", nbPlaces=" + nbPlaces + ", price=" + price + "]";
	}

	public int getNbAcceptedPeople() {
		int nbAcceptedPeople = 0;

		if (applications != null) {
			for (Application app : applications) {
				if (app.getStatut() == EnumStatut.ACCEPTED) {
					nbAcceptedPeople += app.getNbPlaces();
				}
			}
		}

		return nbAcceptedPeople;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}

	public void setNbAcceptedPeople(int nbAcceptedPeople) {
		this.nbAcceptedPeople = nbAcceptedPeople;
	}
}
