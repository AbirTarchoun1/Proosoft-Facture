package tn.proosoftcloud.payload.request;
import lombok.*;
import tn.proosoftcloud.enumerations.AccountStatus;
import tn.proosoftcloud.validator.PasswordMatches;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@PasswordMatches
public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private String matchingPassword;
    private Set<String> role;
    private AccountStatus accountStatus;


    public Set<String> getRole() {
      return this.role;
    }

    public void setRole(Set<String> role) {
      this.role = role;
    }


}
