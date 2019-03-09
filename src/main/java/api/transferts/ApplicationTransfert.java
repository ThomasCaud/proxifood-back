package api.transferts;

public class ApplicationTransfert {
	private Long applicantId;
	private Long offerId;
	private int nbPlaces;
	private boolean helpCooking;

	public ApplicationTransfert() {
		super();
		applicantId = null;
		offerId = null;
		nbPlaces = -1;
		helpCooking = false;
	}

	public Long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
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

	@Override
	public String toString() {
		return "ApplicationTransfert [applicantId=" + applicantId + ", offerId=" + offerId + ", nbPlaces=" + nbPlaces
				+ ", helpCooking=" + helpCooking + "]";
	}

}
