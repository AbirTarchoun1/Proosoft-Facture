package tn.proosoftcloud.entities;
import lombok.*;
import tn.proosoftcloud.enumerations.ERole;
import javax.persistence.*;

@Data
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name="role")
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {
        super();
    }

    public Role(ERole name) {
        this.name = name;
    }

    public Role(String name) {
        this.name= ERole.valueOf(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}

