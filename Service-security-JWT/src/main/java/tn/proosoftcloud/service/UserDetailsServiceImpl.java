package tn.proosoftcloud.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.proosoftcloud.entities.User;
import tn.proosoftcloud.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    IUserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User account = userRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		return UserDetailsImpl.build(account);
	}


}