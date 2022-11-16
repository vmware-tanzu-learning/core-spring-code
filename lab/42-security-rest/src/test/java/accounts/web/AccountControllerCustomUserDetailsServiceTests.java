package accounts.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import accounts.AccountManager;
import accounts.RestWsApplication;
import accounts.security.CustomUserDetailsService;
import accounts.services.AccountService;
import config.RestSecurityConfig;
import rewards.internal.account.Account;

// TODO-16 (Optional): Perform security testing for the two users added
//          through custom UserDetailsService
// - Take some time to understand what each test is for
// - Remove @Disabled annotation from each test and run it
// - Make sure all tests pass

@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = {RestWsApplication.class, RestSecurityConfig.class, CustomUserDetailsService.class})
public class AccountControllerCustomUserDetailsServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountManager accountManager;

    @MockBean
    private AccountService accountService;

    @Test
    @Disabled
    @WithUserDetails("joe")
    public void accountDetails_with_joe_credentials_should_return_200() throws Exception {

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
    @Disabled
    @WithUserDetails("mary")
    public void accountDetails_with_mary_credentials_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0")).andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe")).andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

}

