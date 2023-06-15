package tn.proosoftcloud.sec.service;

import tn.proosoftcloud.sec.entities.Role;
import tn.proosoftcloud.sec.entities.User;
import tn.proosoftcloud.sec.repo.IRoleRepository;
import tn.proosoftcloud.sec.repo.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private IUserRepository iUserRepository;
    private IRoleRepository iRoleRepository;
    private PasswordEncoder passwordEncoder ;
    public AccountServiceImpl(IUserRepository iUserRepository, IRoleRepository iRoleRepository, PasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.iRoleRepository = iRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addNewUser(User user) {
        String pw= user.getPassword();
        user.setPassword(passwordEncoder.encode(pw));
        return iUserRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {

        return iRoleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = iUserRepository.findByUsername(username);
        Role role = iRoleRepository.findByRoleName(roleName);
        user.getRoles().add(role);
     }

    @Override
    public User loadUserByUsername(String username) {

        return iUserRepository.findByUsername(username);
    }

    @Override
    public List<User> listUsers() {

        return iUserRepository.findAll();
    }
}
