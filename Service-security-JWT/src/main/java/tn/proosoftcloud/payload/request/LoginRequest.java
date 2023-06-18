package tn.proosoftcloud.payload.request;

import lombok.Data;
import lombok.Setter;

/**
 * @author  Tarchoun Abir
 *
 */
@Data
@Setter
public class LoginRequest {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
