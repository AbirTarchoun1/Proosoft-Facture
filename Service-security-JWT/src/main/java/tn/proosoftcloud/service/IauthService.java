package tn.proosoftcloud.service;
import tn.proosoftcloud.entities.User;
import tn.proosoftcloud.enumerations.AccountStatus;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

/***********************
 *
 * @author Tarchoun Abir
 *
 *************/
public interface IauthService {

    UserDetailsImpl getIdentity();
    public User updateSatus(Long id, AccountStatus status) throws MessagingException;

    User save(User account);

    public User findById(Long id);

    public List<User> findAll();

    public void delete(Long id);


    void updateCUrrentUser(String username, String email,Long idUser);

}
