package tn.proosoftcloud.sec.service;

import tn.proosoftcloud.sec.entities.Role;
import tn.proosoftcloud.sec.entities.User;

import java.util.List;

public interface AccountService {
    User addNewUser(User user);
    Role addNewRole(Role role);
    void addRoleToUser(String username , String roleName);
    User loadUserByUsername(String username);
    List<User> listUsers();
}
