package tn.proosoftcloud.repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.proosoftcloud.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    //User findByUsername(String username);
    User findByCodeClient(String codeClient);
     User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    public User findByEmail(String email);



    @Modifying
    @Transactional
    @Query(value="update users a set  username= :username, email= :email ,  where a.userId = :userId",nativeQuery = true)
    void updateCUrrentUser(@Param("username") String username, @Param("email") String email, @Param("userId")Long userId);


}
