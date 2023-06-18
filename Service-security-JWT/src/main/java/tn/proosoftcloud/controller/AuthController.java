package tn.proosoftcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.proosoftcloud.config.EmailService;
import tn.proosoftcloud.entities.Role;
import tn.proosoftcloud.entities.User;
import tn.proosoftcloud.enumerations.AccountStatus;
import tn.proosoftcloud.enumerations.ERole;
import tn.proosoftcloud.payload.request.LoginRequest;
import tn.proosoftcloud.payload.request.SignupRequest;
import tn.proosoftcloud.payload.response.JwtResponse;
import tn.proosoftcloud.payload.response.MessageResponse;
import tn.proosoftcloud.repository.IRoleRepository;
import tn.proosoftcloud.repository.IUserRepository;
import tn.proosoftcloud.security.jwt.JwtUtils;
import tn.proosoftcloud.service.UserDetailsImpl;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final EmailService emailService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, IUserRepository userRepository,
						  IRoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
						  EmailService emailService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
		this.emailService = emailService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream()
				.map(role -> role.getAuthority())
				.collect(Collectors.toList());

		if (userDetails.getAccountStatus() == AccountStatus.ACTIVE) {
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), roles, userDetails.getAccountStatus()));
		} else if (userDetails.getAccountStatus() == AccountStatus.PENDING) {
			return ResponseEntity.ok("ERROR: YOUR ACCOUNT IS STILL NOT ACTIVE");
		} else if (userDetails.getAccountStatus() == AccountStatus.BLOCKED) {
			return ResponseEntity.ok("ERROR: YOUR ACCOUNT IS BLOCKED");
		}

		return null;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				passwordEncoder.encode(signUpRequest.getPassword()), passwordEncoder.encode(signUpRequest.getMatchingPassword()),
				AccountStatus.PENDING);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					case "mod":
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
						break;
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Your account has been registered successfully!"));
	}

}