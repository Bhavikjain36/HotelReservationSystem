package ca.sheridancollege.jainbh.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.jainbh.beans.Role;
import ca.sheridancollege.jainbh.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		// Find the user based on the user name
		ca.sheridancollege.jainbh.beans.User user = userRepo.findByUsername(username);
		
		// If the user doesn't exist throw an exception
		if (user == null) {
			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		
		// Change the list of the user's roles into a list of GrantedAuthority
		List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
		for (Role role: user.getRoles()) {
			grantList.add(new SimpleGrantedAuthority(role.getRolename()));
		}
		
		// Create a user based on the information above.
		UserDetails userDetails= (UserDetails) new User(user.getUsername(), user.getEncryptedPassword(), grantList);
		return userDetails;
	}

}
