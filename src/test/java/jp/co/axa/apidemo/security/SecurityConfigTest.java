package jp.co.axa.apidemo.security;

import static org.junit.Assert.assertTrue;

import jp.co.axa.apidemo.auth.ApplicationUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * SecurityConfigTest class
 * test the validity of ApplicationUserDetailsService
 * and SecurityConfig
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    ApplicationUserDetailsService applicationUserDetailsService;

    @Test(expected = UsernameNotFoundException.class)
    public void loadUser_emptyUsername_Test() {
        String searchedUser = "";
        applicationUserDetailsService.loadUserByUsername(searchedUser);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUser_wrongUsername_Test() {
        String searchedUser = "test";
        applicationUserDetailsService.loadUserByUsername(searchedUser);
    }

    @Test
    public void loadUserByUsername_Test() {
        String searchedUser = "user";
        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(searchedUser);
        Optional<? extends org.springframework.security.core.GrantedAuthority>
                searchedRole = userDetails.getAuthorities().stream().findFirst();
        assertTrue(searchedRole.get().toString().equals("ROLE_USER"));
    }

}
