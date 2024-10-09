package lv.wings.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import lv.wings.model.security.MyUser;
import lv.wings.repo.security.IMyUserRepo;

@Service
public class MyUserDetailsMenager implements UserDetailsManager{

	@Autowired
	private IMyUserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MyUser userFromDB = userRepo.findByUsername(username);
		
		if(userFromDB == null) {
			throw new UsernameNotFoundException(username + " is not found in system");
		}else {
			MyUserDetails details = new  MyUserDetails(userFromDB);
			return details;
		}
	}

	@Override
	public void createUser(UserDetails user) {
		// TODO papildināt
		
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO papildināt
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO papildināt
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO papildināt
		
	}

	@Override
	public boolean userExists(String username) {
		// TODO papildināt
		return false;
	}

}
