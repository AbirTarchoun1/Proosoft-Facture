
package tn.proosoftcloud.controller;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import tn.proosoftcloud.config.EmailService;
import tn.proosoftcloud.entities.User;
import tn.proosoftcloud.enumerations.AccountStatus;
import tn.proosoftcloud.payload.request.UpdateProfilRequest;
import tn.proosoftcloud.payload.response.MessageResponse;
import tn.proosoftcloud.repository.IUserRepository;
import tn.proosoftcloud.service.IauthService;
import javax.mail.MessagingException;

/**
 * @author Tarchoun Abir
 *
*/

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {

    public static final String EMAIL_SENT = "An email with a new password was sent to: ";
    private final String PRIVATE_API = "/api/private/user";


    @Autowired
    IUserRepository accountRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    IauthService accountService ;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    @RequestMapping(value = PRIVATE_API, method = RequestMethod.POST)

    ResponseEntity<User> save(@RequestBody User account) {
        return new ResponseEntity<>(accountService.save(account), HttpStatus.CREATED);
    }


    @RequestMapping(value = PRIVATE_API, method = RequestMethod.GET)
    public ResponseEntity<Collection<User>>view() {
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }


    /*@PreAuthorize( "hasRole('MODERATOR') or hasRole('ADMIN')")*/
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)

    public User findById(@PathVariable Long id) {
     return accountService.findById(id);
    }



    @RequestMapping(value = PRIVATE_API, method = RequestMethod.PUT)

    public ResponseEntity<User> update(@RequestBody User user ) {
        return new ResponseEntity<>(accountService.save(user),HttpStatus.CREATED);
    }

    @RequestMapping(value = PRIVATE_API + "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = PRIVATE_API + "/toggle-status/{id}", method = RequestMethod.PUT)

    public User updateSatus(@PathVariable("id")  Long id, @RequestBody AccountStatus status) throws MessagingException {
        return accountService.updateSatus(id, status);
    }

    // <=============change password=====================>
   /* @PostMapping(PRIVATE_API +"/change-password")

    public boolean changePassword(@RequestBody ChangePasswordRequest request) {
        return accountService.changePassword(request);
    }*/


    //get current user identity
    @GetMapping(PRIVATE_API + "/me")

    public UserDetails getIdentity() {
        return accountService.getIdentity();

    }

    //  change  password <=============> update by email

    @PreAuthorize( "hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping(PRIVATE_API + "/resetpasswordtoken/{email}")

    public ResponseEntity<String> resetPasswordToken(@PathVariable("email") String email)  throws MessagingException {
        User user = accountRepository.findByEmail(email);
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        accountService.save(user);
        emailService.sendNewPasswordEmail(user.getUsername(), password, user.getEmail());
        return ResponseEntity.ok().body(EMAIL_SENT + email);
    }
    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }



    /**
     * @param
     *
     */
    @RequestMapping(value = PRIVATE_API + "/update-currentUser/{userName}/{email}/{fiscalCode}/{idAccount}", method = RequestMethod.GET)

    ResponseEntity  updateCUrrentUserAnotherMethod (@PathVariable String userName,@PathVariable String email,@PathVariable String fiscalCode,@PathVariable Long idUser) {

        accountService.updateCUrrentUser(userName,email,idUser);
        return ResponseEntity.ok(new MessageResponse("user updated successfully"));
    }

    @RequestMapping(value = PRIVATE_API + "/update-currentUser", method = RequestMethod.PUT)

    ResponseEntity  updateCUrrentUser (@RequestBody UpdateProfilRequest updateProfilRequest) {

        accountService.updateCUrrentUser(updateProfilRequest.getUsername(),updateProfilRequest.getEmail(),updateProfilRequest.getIdAccount());
        return ResponseEntity.ok(new MessageResponse("user updated successfully"));
    }


    @GetMapping("/{codeClient}")
    public ResponseEntity<User> getUserByCodeClient(@PathVariable("codeClient") String codeClient) {
        User user = accountRepository.findByCodeClient(codeClient);
        return ResponseEntity.ok(user);
    }


}


