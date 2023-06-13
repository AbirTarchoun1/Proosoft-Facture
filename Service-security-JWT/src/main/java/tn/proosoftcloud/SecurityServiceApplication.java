package tn.proosoftcloud;

import tn.proosoftcloud.sec.entities.AppRole;
import tn.proosoftcloud.sec.entities.AppUser;
import tn.proosoftcloud.sec.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
@EnableDiscoveryClient

@SpringBootApplication
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner start(AccountService accountService){
        return  args -> {
            accountService.addNewRole(new AppRole(null,"Admin"));
            //accountService.addNewRole(new AppRole(null,"Client"));
            accountService.addNewRole(new AppRole(null,"User"));

            accountService.addNewUser( new AppUser(null,"admin","117896546","abir@tp-dev.net","24948559","tpdev*",new ArrayList<>()));
            accountService.addNewUser( new AppUser(null,"user","1136987454","user@gmail.com","24948559","tpdev*",new ArrayList<>()));


            accountService.addRoleToUser("admin","Admin");
            accountService.addRoleToUser("user","User");



        };
    }
}
