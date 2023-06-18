package tn.proosoftcloud.payload.request;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tarchoun Abir
 *
 */
@Getter
@Setter
public class UpdateProfilRequest {
    //lezem dima nabth nafs esem el request ta entity
    private Long idAccount;
    private String username;
    private String email;
}
