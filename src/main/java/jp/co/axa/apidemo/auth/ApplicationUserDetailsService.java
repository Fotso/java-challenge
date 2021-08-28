package jp.co.axa.apidemo.auth;

import java.util.Optional;
import jp.co.axa.apidemo.entities.UserInfo;
import jp.co.axa.apidemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * ApplicationUserDetailsService
 * Service that define the user's search by a user's name.
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserInfo> user = userRepository.findUserByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s is invalid or does not exist.", userName)));
        return user.map(ApplicationUserDetails::new).get();
    }
}
