package accounts.web;

import accounts.AccountManager;
import accounts.RestWsApplication;
import accounts.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.money.Percentage;
import config.RestSecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import rewards.internal.account.Account;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO-06a: Perform security testing against MVC layer
// - Take some time to understand what each test is for
// - Remove @Disabled annotation from each test and run it
// - Make sure all tests pass

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
    @Disabled
    @WithMockUser(roles = {"INVALID"})
    void accountSummary_with_invalid_role_should_return_403() throws Exception {

        mockMvc.perform(get("/accounts"))
               .andExpect(status().isForbidden());
    }

    @Test
    @Disabled
    @WithMockUser( roles = {"USER"})
    public void accountDetails_with_USER_role_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe")).andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

    @Test
    @Disabled
    @WithMockUser(username = "user", password = "user")
    public void accountDetails_with_user_credentials_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe")).andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

    @Test
    @Disabled
    @WithMockUser(username = "admin", password = "admin")
    public void accountDetails_with_admin_credentials_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe")).andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

    @Test
    @Disabled
    @WithMockUser(username = "superadmin", password = "superadmin")
    public void accountDetails_with_superadmin_credentials_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe"))
               .andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

    @Test
    @Disabled
    @WithMockUser(roles = {"USER"})
    public void accountDetailsFail_test_with_USER_role_should_proceed_successfully() throws Exception {

        given(accountManager.getAccount(any(Long.class)))
                .willThrow(new IllegalArgumentException("No such account with id " + 0L));

        mockMvc.perform(get("/accounts/0"))
               .andExpect(status().isNotFound());

        verify(accountManager).getAccount(any(Long.class));

    }

    @Test
    @Disabled
    @WithMockUser(roles = {"ADMIN"})
    public void accountSummary_with_ADMIN_role_should_return_200() throws Exception {

        List<Account> testAccounts = Arrays.asList(new Account("123456789", "John Doe"));
        given(accountManager.getAllAccounts()).willReturn(testAccounts);

        mockMvc.perform(get("/accounts"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$..number").value("123456789"))
               .andExpect(jsonPath("$..name").value("John Doe"));

        verify(accountManager).getAllAccounts();

    }

    @Test
    @Disabled
    @WithMockUser(roles = {"ADMIN", "SUPERADMIN"})
    public void createAccount_with_ADMIN_or_SUPERADMIN_role_should_return_201() throws Exception {

        Account testAccount = new Account("1234512345", "Mary Jones");
        testAccount.setEntityId(21L);
        given(accountManager.save(any(Account.class))).willReturn(testAccount);

        mockMvc.perform(post("/accounts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(testAccount)))
               .andExpect(status().isCreated())
               .andExpect(header().string("Location", "http://localhost/accounts/21"));

        verify(accountManager).save(any(Account.class));

    }

    // TODO-06b: Write a test that verifies that a user with "USER" role
    //          is not permitted to perform POST operation
    // - Use the code above (in the previous test) as a guidance
    //   but without using "given" and "verify" methods.
    //   (The "given" and "verify" methods are not required for
    //    this testing because security failure will prevent
    //    calling a method of a dependency.)
    @Test
    public void createAccount_with_USER_role_should_return_403() throws Exception {



    }

    @Test
    @Disabled
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
    @Disabled
    @WithMockUser(roles = {"ADMIN", "SUPERADMIN"})
    public void addBeneficiary_with_ADMIN_or_SUPERADMIN_role_should_return_201() throws Exception {

        mockMvc.perform(post("/accounts/{entityId}/beneficiaries", 0L).content("Kate"))
               .andExpect(status().isCreated())
               .andExpect(header().string("Location", "http://localhost/accounts/0/beneficiaries/Kate"));
    }

    @Test
    @Disabled
    @WithMockUser(roles = {"USER"})
    public void addBeneficiary_with_USER_role_should_return_403() throws Exception {

        mockMvc.perform(post("/accounts/{entityId}/beneficiaries", 0L).content("Kate"))
               .andExpect(status().isForbidden());
    }

    @Test
    @Disabled
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
    @Disabled
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void removeBeneficiary_with_USER_or_ADMIN_role_should_return_403() throws Exception {

        Account account = new Account("1234567890", "John Doe");
        account.addBeneficiary("Corgan", new Percentage(0.1));
        given(accountManager.getAccount(0L)).willReturn(account);

        mockMvc.perform(delete("/accounts/{entityId}/beneficiaries/{name}", 0L, "Corgan"))
               .andExpect(status().isForbidden());

    }

    @Test
    @Disabled
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

