package tn.proosoftcloud.sec.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import tn.proosoftcloud.sec.JWTUtil;
import tn.proosoftcloud.sec.entities.Role;
import tn.proosoftcloud.sec.entities.User;
import tn.proosoftcloud.sec.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/users")
    public List<User> appUsers() {
        return accountService.listUsers();

    }

    @PostMapping(path = "/users")
    public User saveUser(@RequestBody User user) {
        return accountService.addNewUser(user);

    }

    @PostMapping(path = "/roles")
    public Role saveRole(@RequestBody Role role) {
        return accountService.addNewRole(role);

    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRolename());

    }

    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String auhToken = request.getHeader(JWTUtil.AUTH_HEADER);
        if (auhToken != null && auhToken.startsWith(JWTUtil.PREFIX)) {
            try {

                String jwt = auhToken.substring(JWTUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                User user = accountService.loadUserByUsername(username);
                // craete new acces token from refresh token

                String jwtAccessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_REFRESH_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                //return refresh token and acces token in hash map object
                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            } catch (Exception e) {
                throw e;

            }

        }else{
            throw new RuntimeException("Refresh token required !! ");
        }

        }
        @GetMapping(path="/profile")
        public User profile(Principal principal){
        return accountService.loadUserByUsername(principal.getName());
        }


    }

    @Data
    class RoleUserForm {
        private String username;
        private String rolename;
    }

