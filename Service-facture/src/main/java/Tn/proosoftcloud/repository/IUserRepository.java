package Tn.proosoftcloud.repository;

import Tn.proosoftcloud.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
