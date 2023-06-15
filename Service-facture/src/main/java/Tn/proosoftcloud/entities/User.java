package Tn.proosoftcloud.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Entity
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
   /* @OneToMany(mappedBy = "user")
    private List<Facture> factures;*/

    public User (Long iduser, String username ,String cin,String email, String codeClient, String password ){
        this.iduser = iduser;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

public User (){

}
}
