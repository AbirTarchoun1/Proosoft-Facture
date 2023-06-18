package tn.proosoftcloud.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.proosoftcloud.enumerations.AccountStatus;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor

@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser ;
    private String username ;
    private String cin ;
    private String email ;
    private String codeClient ;
    private String matchingPassword;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password ;
    private AccountStatus accountStatus;

    /**
     * Role
     *
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private List<Facture> factures;

    public <E> User(String username , String cin, String email, String codeClient, String passwor) {

        this.username = username;
        this.cin = cin;
        this.email = email;
        this.codeClient = codeClient;
        this.password = password;
    }

    public User(String username, String email, String encode, String encode1, AccountStatus accountStatus) {
        this.username=username;
        this.password = encode;
        this.matchingPassword=encode1;
        this.email=email;
        this.accountStatus=accountStatus;}


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public User(Long idUser, String username, String cin, String email, String codeClient, String password, Collection<Role> roles, List<Facture> factures) {
        this.idUser = idUser;
        this.username = username;
        this.cin = cin;
        this.email = email;
        this.codeClient = codeClient;
        this.password = password;
        this.roles = (Set<Role>) roles;
        this.factures = factures;
    }



}
