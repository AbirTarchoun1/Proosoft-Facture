package tn.proosoftcloud.sec.service;

import tn.proosoftcloud.sec.entities.AppRole;
import tn.proosoftcloud.sec.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username , String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
