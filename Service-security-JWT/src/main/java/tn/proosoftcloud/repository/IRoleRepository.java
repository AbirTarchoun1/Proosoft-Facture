package tn.proosoftcloud.repository;
import tn.proosoftcloud.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.proosoftcloud.enumerations.ERole;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role,Long>{

    Optional<Role> findByName(ERole name);

}
