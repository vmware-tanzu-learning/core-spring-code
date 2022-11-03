package accounts.client;

import accounts.RestWsApplication;
import common.money.Percentage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import java.net.URI;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

// TODO-07a: Perform security testing against a running server
// - Take some time to understand what each test is for
// - Remove @Disabled annotation from each test and run it
// - Make sure all tests pass

@SpringBootTest(classes = {RestWsApplication.class},
        webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountClientTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private Random random = new Random();

    @Test
    @Disabled
    public void listAccounts_using_invalid_user_should_return_401() throws Exception {
        ResponseEntity<String> responseEntity
                = restTemplate.withBasicAuth("invalid", "invalid")
                              .getForEntity("/accounts", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Disabled
    public void listAccounts_using_valid_user_should_succeed() {
        String url = "/accounts";
        // we have to use Account[] instead of List<Account>, or Jackson won't know what type to unmarshal to
        ResponseEntity<Account[]> responseEntity
                = restTemplate.withBasicAuth("user", "user")
                              .getForEntity(url, Account[].class);
        Account[] accounts = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(accounts.length >= 21).withFailMessage("Expected 21 accounts, but found " + accounts.length).isTrue();
        assertThat(accounts[0].getName()).isEqualTo("Keith and Keri Donald");
        assertThat(accounts[0].getBeneficiaries().size()).isEqualTo(2);
        assertThat(accounts[0].getBeneficiary("Annabelle").getAllocationPercentage()).isEqualTo(Percentage.valueOf("50%"));
    }

    @Test
    @Disabled
    public void listAccounts_using_valid_admin_should_succeed() {
        String url = "/accounts";
        // we have to use Account[] instead of List<Account>, or Jackson won't know what type to unmarshal to
        ResponseEntity<Account[]> responseEntity
                = restTemplate.withBasicAuth("admin", "admin")
                              .getForEntity(url, Account[].class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Account[] accounts = responseEntity.getBody();
        assertThat(accounts.length >= 21).withFailMessage("Expected 21 accounts, but found " + accounts.length).isTrue();
        assertThat(accounts[0].getName()).isEqualTo("Keith and Keri Donald");
        assertThat(accounts[0].getBeneficiaries().size()).isEqualTo(2);
        assertThat(accounts[0].getBeneficiary("Annabelle").getAllocationPercentage()).isEqualTo(Percentage.valueOf("50%"));
    }

    @Test
    @Disabled
    public void getAccount_using_valid_user_should_succeed() {
        String url = "/accounts/{accountId}";
        ResponseEntity<Account> responseEntity
                = restTemplate.withBasicAuth("user", "user")
                              .getForEntity(url, Account.class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Account account = responseEntity.getBody();
        assertThat(account.getName()).isEqualTo("Keith and Keri Donald");
        assertThat(account.getBeneficiaries().size()).isEqualTo(2);
        assertThat(account.getBeneficiary("Annabelle").getAllocationPercentage()).isEqualTo(Percentage.valueOf("50%"));
    }

    @Test
    @Disabled
    public void createAccount_using_admin_should_return_201() {
        String url = "/accounts";
        // use a unique number to avoid conflicts
        String number = String.format("12345%4d", random.nextInt(10000));
        Account account = new Account(number, "John Doe");
        account.addBeneficiary("Jane Doe");
        ResponseEntity<Void> responseEntity
                = restTemplate.withBasicAuth("admin", "admin")
                              .postForEntity(url, account, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    // TODO-07b: Write a test that verifies that "user"/"user"
    //          is not permitted to create a new Account
    // - Use the code above as a guidance
    @Test
    public void createAccount_using_user_should_return_403() throws Exception {



    }

    @Test
    @Disabled
    public void addAndDeleteBeneficiary_using_superadmin_should_succeed() {
        // perform both add and delete to avoid issues with side effects
        String addUrl = "/accounts/{accountId}/beneficiaries";
        URI newBeneficiaryLocation
                = restTemplate.withBasicAuth("superadmin", "superadmin")
                              .postForLocation(addUrl, "David", 1);

        Beneficiary newBeneficiary
                = restTemplate.withBasicAuth("superadmin", "superadmin")
                              .getForObject(newBeneficiaryLocation, Beneficiary.class);
        assertThat(newBeneficiary.getName()).isEqualTo("David");

        restTemplate.withBasicAuth("superadmin", "superadmin")
                    .delete(newBeneficiaryLocation);

        // use exchange method to receive a 404 response
        ResponseEntity<Beneficiary> response =
                restTemplate.withBasicAuth("superadmin", "superadmin")
                            .getForEntity(newBeneficiaryLocation, Beneficiary.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
