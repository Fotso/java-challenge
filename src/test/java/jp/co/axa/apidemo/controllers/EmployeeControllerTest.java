package jp.co.axa.apidemo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * EmployeeControllerTest
 * EmployeeControllerTest tests the controller endpoints accesses
 * with the different mock users
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "", password = "", roles = "")
    public void test_unexitent_user() throws Exception {
        mockMvc.perform(get("/api/v1/employees")).andExpect(status().isForbidden());
    }

    @Test
    public void test_authorized_homeEndpoint() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void test_authorized_SwaggerEndpoint() throws Exception {
        mockMvc.perform(get("/swagger-ui.html")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "test")
    public void test_unauthorized_user() throws Exception {
        mockMvc.perform(get("/api/v1/employees")).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void test_authorized_user() throws Exception {
        mockMvc.perform(get("/api/v1/employees")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void test_authorized_delete_admin_ROLE() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/4")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void test_unauthorized_delete_user_ROLE() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/4")).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

}
