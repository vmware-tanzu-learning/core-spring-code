package accounts.web;

import auth.AuthorizationServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = AuthorizationServer.class)
@TestPropertySource("/auth-server.properties")
public class AccountControllerSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "superuser", password = "superuser", roles = {"SUPERUSER"})
    void superuser_only_page_can_be_accessed_by_SUPERUSER_role() throws Exception {

        mockMvc.perform(get("/superuser-only"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void superuser_only_page_CANNOT_be_accessed_by_ADMIN_role() throws Exception {

        mockMvc.perform(get("/superuser-only"))
               .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void dashboard_page_can_be_accessed_by_ADMIN_role() throws Exception {

        mockMvc.perform(get("/dashboard"))
               .andExpect(status().isOk());
    }

}
