package api.transferts;

import java.util.Date;

public class OfferTransfert {
	private Long creatorId;
	private boolean allowHelpCooking;
	private Date date;
	private Date endOfInscription;
	private String meal;
	private String description;
	private Integer nbPlaces;
	private float price;

	public OfferTransfert() {
		super();
		setMeal("");
		setDescription("");
		setNbPlaces(-1);
		setPrice(-1);
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
		return "OfferTransfert [creatorId=" + creatorId + ", allowHelpCooking=" + allowHelpCooking + ", date=" + date
				+ ", endOfInscription=" + endOfInscription + ", meal=" + meal + ", description=" + description
				+ ", nbPlaces=" + nbPlaces + ", price=" + price + "]";
	}
}
