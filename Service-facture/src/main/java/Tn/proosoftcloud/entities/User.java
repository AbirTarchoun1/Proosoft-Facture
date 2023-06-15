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
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToMany(mappedBy = "user")
    private List<Facture> factures;

}
