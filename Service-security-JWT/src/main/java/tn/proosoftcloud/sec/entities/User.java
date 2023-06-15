package tn.proosoftcloud.sec.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor

@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iduser ;
    private String username ;
    private String cin ;
    private String email ;
    private String codeClient ;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password ;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles =new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Facture> factures;

    public <E> User(String username , String cin, String email, String codeClient, String passwor) {

        this.username = username;
        this.cin = cin;
        this.email = email;
        this.codeClient = codeClient;
        this.password = password;
    }


    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public User(Long iduser, String username, String cin, String email, String codeClient, String password, Collection<Role> roles, List<Facture> factures) {
        this.iduser = iduser;
        this.username = username;
        this.cin = cin;
        this.email = email;
        this.codeClient = codeClient;
        this.password = password;
        this.roles = roles;
        this.factures = factures;
    }
}
