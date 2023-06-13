package tn.proosoftcloud.sec.repo;

import tn.proosoftcloud.sec.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long>{

    AppRole findByRoleName(String roleName) ;
}
