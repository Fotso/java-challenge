package jp.co.axa.apidemo.auth;

import java.util.ArrayList;
import java.util.List;
import jp.co.axa.apidemo.entities.UserInfo;
import jp.co.axa.apidemo.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertTrue;

/**
 * ApplicationUserDetailsTest
 * creates mock users and test if the users
 * created during the project initialization
 * are the correct one.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationUserDetailsTest {

    private UserInfo adminUserTest;
    private UserInfo simpleUserTest;
    private List<UserInfo> userInfoList;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        userInfoList = userRepository.findAll();

        //user with USER right
        Long simpleUserId = 1L;
        String simpleUser_userName = "user";
        String simpleUser_password = "user";
        Boolean simpleUser_isActive = true;
        String simpleUser_authorities = "ROLE_USER,employee:read";
        simpleUserTest = createUserInfo(simpleUserId, simpleUser_userName, simpleUser_password, simpleUser_isActive, simpleUser_authorities);

        //user with ADMIN right
        Long adminUser_id = 2L;
        String adminUser_userName = "admin";
        String adminUser_password = "admin";
        Boolean adminUser_isActive = true;
        String adminUser_authorities = "ROLE_ADMIN,employee:read,employee:write,employee:edit";
        adminUserTest = createUserInfo(adminUser_id, adminUser_userName, adminUser_password, adminUser_isActive, adminUser_authorities);
    }

    @Test
    public void verify_InitializeData_NotNull_Test() {
        assertTrue(userInfoList.stream().findAny().isPresent());
    }

    @Test
    public void verify_InitializeData_UserInfoListSize_Test() {
        assertTrue(userInfoList.size() == 2);
    }

    @Test
    public void verify_InitializeData_correctData_Test() {
        List<UserInfo> mockList = new ArrayList<>();
        mockList.add(simpleUserTest);
        mockList.add(adminUserTest);
        assertTrue(userInfoList.containsAll(mockList));
    }

    public UserInfo createUserInfo(Long id, String userName, String password, Boolean isActive, String authorities) {
        return new UserInfo(id, userName, password, isActive, authorities);
    }

}
