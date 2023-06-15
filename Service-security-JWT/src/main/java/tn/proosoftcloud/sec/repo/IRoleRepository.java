package tn.proosoftcloud.sec.repo;

import tn.proosoftcloud.sec.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Long>{

    Role findByRoleName(String roleName) ;
}
