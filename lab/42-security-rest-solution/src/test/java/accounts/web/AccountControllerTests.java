package accounts.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import accounts.AccountManager;
import accounts.RestWsApplication;
import accounts.services.AccountService;
import common.money.Percentage;
import config.RestSecurityConfig;
import rewards.internal.account.Account;

@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = {RestWsApplication.class, RestSecurityConfig.class})
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountManager accountManager;

    @MockBean
    private AccountService accountService;

    @Test
    @WithMockUser(roles = {"INVALID"})
    void accountSummary_with_invalid_role_should_return_403() throws Exception {

        mockMvc.perform(get("/accounts"))
               .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser( roles = {"USER"})
    public void accountDetails_with_USER_role_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0")).andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe")).andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void accountDetails_with_user_credentials_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0")).andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe"))
               .andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void accountDetails_with_admin_credentials_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0")).andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe")).andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

    @Test
    @WithMockUser(username = "superadmin", password = "superadmin")
    public void accountDetails_with_superadmin_credentials_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0")).andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe")).andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void accountDetailsFail_test_with_USER_role_should_proceed_successfully() throws Exception {

        given(accountManager.getAccount(any(Long.class)))
                .willThrow(new IllegalArgumentException("No such account with id " + 0L));

        mockMvc.perform(get("/accounts/0")).andExpect(status().isNotFound());

        verify(accountManager).getAccount(any(Long.class));

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void accountSummary_with_ADMIN_role_should_return_200() throws Exception {

        List<Account> testAccounts = Arrays.asList(new Account("123456789", "John Doe"));
        given(accountManager.getAllAccounts()).willReturn(testAccounts);

        mockMvc.perform(get("/accounts")).andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$..number").value("123456789")).andExpect(jsonPath("$..name").value("John Doe"));

        verify(accountManager).getAllAccounts();

    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPERADMIN"})
    public void createAccount_with_ADMIN_or_SUPERADMIN_role_should_return_201() throws Exception {

        Account testAccount = new Account("1234512345", "Mary Jones");
        testAccount.setEntityId(21L);
        given(accountManager.save(any(Account.class))).willReturn(testAccount);

        mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
                                         .content(asJsonString(testAccount)).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(header().string("Location", "http://localhost/accounts/21"));

        verify(accountManager).save(any(Account.class));

    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void createAccount_with_USER_role_should_return_403() throws Exception {

        Account testAccount = new Account("1234512345", "Mary Jones");
        testAccount.setEntityId(21L);

        mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
                                         .content(asJsonString(testAccount)).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = {"SUPERADMIN"})
    public void getBeneficiary_with_SUPERADMIN_role_should_return_200() throws Exception {

        Account account = new Account("1234567890", "John Doe");
        account.addBeneficiary("Corgan", new Percentage(0.1));
        given(accountManager.getAccount(0L)).willReturn(account);

        mockMvc.perform(get("/accounts/{accountId}/beneficiaries/{beneficiaryName}", 0L, "Corgan"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("Corgan"))
               .andExpect(jsonPath("allocationPercentage").value("0.1"));

        verify(accountManager).getAccount(0L);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPERADMIN"})
    public void addBeneficiary_with_ADMIN_or_SUPERADMIN_role_should_return_201() throws Exception {

        mockMvc.perform(post("/accounts/{entityId}/beneficiaries", 0L).content("Kate"))
               .andExpect(status().isCreated())
               .andExpect(header().string("Location", "http://localhost/accounts/0/beneficiaries/Kate"));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void addBeneficiary_with_USER_role_should_return_403() throws Exception {

        mockMvc.perform(post("/accounts/{entityId}/beneficiaries", 0L).content("Kate"))
               .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"SUPERADMIN"})
    public void removeBeneficiary_with_SUPERADMIN_role_should_return_204() throws Exception {

        Account account = new Account("1234567890", "John Doe");
        account.addBeneficiary("Corgan", new Percentage(0.1));
        given(accountManager.getAccount(0L)).willReturn(account);

        mockMvc.perform(delete("/accounts/{entityId}/beneficiaries/{name}", 0L, "Corgan"))
               .andExpect(status().isNoContent());

        verify(accountManager).getAccount(0L);

    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void removeBeneficiary_with_USER_or_ADMIN_role_should_return_403() throws Exception {

        Account account = new Account("1234567890", "John Doe");
        account.addBeneficiary("Corgan", new Percentage(0.1));
        given(accountManager.getAccount(0L)).willReturn(account);

        mockMvc.perform(delete("/accounts/{entityId}/beneficiaries/{name}", 0L, "Corgan"))
               .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = {"SUPERADMIN"})
    public void removeBeneficiaryFail_test_with_SUPERADMIN_role_should_proceed_successfully() throws Exception {
        Account account = new Account("1234567890", "John Doe");
        given(accountManager.getAccount(0L)).willReturn(account);

        mockMvc.perform(delete("/accounts/{entityId}/beneficiaries/{name}", 0L, "Noname"))
               .andExpect(status().isNotFound());

        verify(accountManager).getAccount(0L);
    }

    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

