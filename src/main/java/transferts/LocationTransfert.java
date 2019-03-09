package transferts;

public class LocationTransfert {
	private String zipcode;
	private String city;
	private String country;
	private String street;
	private String number;
	private String complementary;
	private Double longitude;
	private Double latitude;
	private String formattedAddress;
	
	
	public LocationTransfert() {
		zipcode = null;
		city = null;
		country = null;
		street = null;
		number = null;
		complementary = null;
		longitude = null;
		latitude = null;
		setFormattedAddress(null);
	}


	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getComplementary() {
		return complementary;
	}
	
	public void setComplementary(String complementary) {
		this.complementary = complementary;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
}
