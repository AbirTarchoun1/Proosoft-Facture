package tn.proosoftcloud.payload.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.proosoftcloud.enumerations.AccountStatus;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor

public class JwtResponse {
	@Getter
	@Setter
	private String token;
	@Getter
	@Setter
	private Long id;
	@Getter
	@Setter
	private String username;
	@Getter
	@Setter
	private String email;
	@Getter
	@Setter
	private AccountStatus accountStatus;
	@Getter
	@Setter
	private List<String> roles;


	public JwtResponse(String token, Long id, String username, String email, List<String> roles,  AccountStatus accountStatus) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.accountStatus = accountStatus;


	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}

