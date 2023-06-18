package tn.proosoftcloud.payload.request;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tarchoun Abir
 *
 */
@Getter
@Setter
public class ChangePasswordRequest {
    private String password;
    private String newPassword;
}
