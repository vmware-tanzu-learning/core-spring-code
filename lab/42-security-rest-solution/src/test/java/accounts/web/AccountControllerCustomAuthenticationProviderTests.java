package accounts.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import accounts.AccountManager;
import accounts.RestWsApplication;
import accounts.security.CustomAuthenticationProvider;
import accounts.services.AccountService;
import config.RestSecurityConfig;
import rewards.internal.account.Account;


@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = {RestWsApplication.class, RestSecurityConfig.class, CustomAuthenticationProvider.class})
public class AccountControllerCustomAuthenticationProviderTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountManager accountManager;

    @MockBean
    private AccountService accountService;

    @Test
    public void accountDetails_with_spring_credentials_should_return_200() throws Exception {

        // arrange
        given(accountManager.getAccount(0L)).willReturn(new Account("1234567890", "John Doe"));

        // act and assert
        mockMvc.perform(get("/accounts/0").with(httpBasic("spring", "spring")))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("name").value("John Doe")).andExpect(jsonPath("number").value("1234567890"));

        // verify
        verify(accountManager).getAccount(0L);

    }

}

