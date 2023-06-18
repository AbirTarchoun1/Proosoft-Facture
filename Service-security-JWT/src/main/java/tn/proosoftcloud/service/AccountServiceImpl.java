package tn.proosoftcloud.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import tn.proosoftcloud.entities.User;
import tn.proosoftcloud.enumerations.AccountStatus;
import tn.proosoftcloud.payload.request.ChangePasswordRequest;
import tn.proosoftcloud.config.EmailService;
import tn.proosoftcloud.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements  IauthService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;


    //====================== Add Account ===============================//
    public User save(User account) {
        account.setAccountStatus(AccountStatus.PENDING);
        User saved = userRepository.save(account);
        return saved;
    }

    //====================== find All===============================//
    public List<User> findAll() {
        return userRepository.findAll();
    }


    //========================= Delete ===============================//
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        userRepository.deleteById(id);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }

    //================================ update Status ===============================//
    @Override
    public User updateSatus(Long id, AccountStatus status) throws MessagingException {
        Optional<User> Data = userRepository.findById(id);
        User saved = null;
        if (Data.isPresent()) {
            User account = Data.get();

            if (AccountStatus.ACTIVE == status) {
                account.setAccountStatus(AccountStatus.BLOCKED);
                emailService.sendNotifBlockedAccount(account.getEmail(), account.getUsername());
            }
            if (AccountStatus.PENDING == status) {
                account.setAccountStatus(AccountStatus.ACTIVE);
                emailService.sendNotif(account.getEmail(), account.getUsername());
            }
            if (AccountStatus.BLOCKED == status) {
                account.setAccountStatus(AccountStatus.PENDING);

            }

            saved = userRepository.save(account);
        }
        return saved;

    }


    //===============================  Changes Password ==============================//


    public boolean changePassword(ChangePasswordRequest request) {
        UserDetailsImpl userDetails = getIdentity();
        if (null == userDetails) {
            return false;
        }

        Optional<User> optionalAccount = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername()));
        if (!optionalAccount.isPresent()) {
            return false;
        }
        User account = optionalAccount.get();
        if (passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(account);
            return true;
        }
        return false;
    }

    /*************
     * identity of user
     */
    public UserDetailsImpl getIdentity() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailsImpl)
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;

    }

    /**
     * @update current user info ==============================>
     */

    @Override
    public void updateCUrrentUser(String username, String email, Long idUser) {
        userRepository.updateCUrrentUser(username, email, idUser);
    }

    public User findByCodeClient(String codeClient) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByCodeClient(codeClient));
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchElementException("User not found with code client: " + codeClient);
        }
    }
    }
