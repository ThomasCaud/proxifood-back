package api.transferts;

public class AuthTransfert {
	private String login;
	private String password;

	public AuthTransfert() {
		super();
		setLogin("");
		setPassword("");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
