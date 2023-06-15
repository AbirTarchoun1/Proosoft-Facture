package tn.proosoftcloud.sec.repo;

import tn.proosoftcloud.sec.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
