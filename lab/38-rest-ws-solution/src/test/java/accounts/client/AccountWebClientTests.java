package accounts.client;

import common.money.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import java.net.URI;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AccountWebClientTests {

    /**
     * server URL ending with the servlet mapping on which the application can be reached.
     */
    private static final String BASE_URL = "http://localhost:8080";

    private Random random = new Random();
    private WebClient webClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.create(BASE_URL);
    }

    @Test
    public void listAccounts_WebClient_retrieve_blocking() {

        Account[] accounts = webClient.get()
                                      .uri("/accounts")
                                      .accept(MediaType.APPLICATION_JSON)
                                      .retrieve()
                                      .bodyToMono(Account[].class)
                                      .block();

        System.out.println(accounts.length);
        assertTrue(accounts.length >= 21, "Expected 21 accounts, but found " + accounts.length);
        assertEquals("Keith and Keri Donald", accounts[0].getName());
        assertEquals(2, accounts[0].getBeneficiaries().size());
        assertEquals(Percentage.valueOf("50%"), accounts[0].getBeneficiary("Annabelle").getAllocationPercentage());
    }

    @Test
    public void listAccounts_WebClient_exchange_blocking() {

        Account[] accounts = webClient.get()
                                      .uri("/accounts")
                                      .accept(MediaType.APPLICATION_JSON)
                                      .exchange()
                                      .flatMap(response -> response.bodyToMono(Account[].class))
                                      .block();

        assertTrue(accounts.length >= 21, "Expected 21 accounts, but found " + accounts.length);
        assertEquals("Keith and Keri Donald", accounts[0].getName());
        assertEquals(2, accounts[0].getBeneficiaries().size());
        assertEquals(Percentage.valueOf("50%"), accounts[0].getBeneficiary("Annabelle").getAllocationPercentage());
    }

    @Test
    public void getAccount_WebClient_retrieve_blocking() {

        Account account = webClient.get()
                                   .uri("/accounts/{id}", 0)
                                   .accept(MediaType.APPLICATION_JSON)
                                   .retrieve()
                                   .bodyToMono(Account.class)
                                   .block();

        assertEquals("Keith and Keri Donald", account.getName());
        assertEquals(2, account.getBeneficiaries().size());
        assertEquals(Percentage.valueOf("50%"), account.getBeneficiary("Annabelle").getAllocationPercentage());
    }

    @Test
    public void getAccount_WebClient_exchange_blocking() {

        Account account = webClient.get()
                                   .uri("/accounts/{id}", 0)
                                   .accept(MediaType.APPLICATION_JSON)
                                   .exchange()
                                   .flatMap(response -> response.bodyToMono(Account.class))
                                   .block();

        assertEquals("Keith and Keri Donald", account.getName());
        assertEquals(2, account.getBeneficiaries().size());
        assertEquals(Percentage.valueOf("50%"), account.getBeneficiary("Annabelle").getAllocationPercentage());
    }

    @Test
    public void createAccount_WebClient_blocking() throws Exception {
        // use a unique number to avoid conflicts
        String number = String.format("12345%4d", random.nextInt(10000));
        Account account = new Account(number, "John Doe");
        account.addBeneficiary("Jane Doe");

        ClientResponse clientResponse = webClient.post()
                                                 .uri("/accounts")
                                                 .contentType(MediaType.APPLICATION_JSON)
                                                 .bodyValue(account)
                                                 .exchange()
                                                 .block();

        URI newAccountLocation = new URI(clientResponse.headers().header("Location").get(0));

        Account retrievedAccount = webClient.get()
                                            .uri(newAccountLocation)
                                            .accept(MediaType.APPLICATION_JSON)
                                            .retrieve()
                                            .bodyToMono(Account.class)
                                            .block();

        assertEquals(account.getNumber(), retrievedAccount.getNumber());

        Beneficiary accountBeneficiary = account.getBeneficiaries().iterator().next();
        Beneficiary retrievedAccountBeneficiary = retrievedAccount.getBeneficiaries().iterator().next();

        assertEquals(accountBeneficiary.getName(), retrievedAccountBeneficiary.getName());
        assertNotNull(retrievedAccount.getEntityId());
    }

    @Test
    public void createSameAccountTwiceResultsIn409_WebClient_blocking() {
        Account account = new Account("123123123", "John Doe");
        account.addBeneficiary("Jane Doe");

        ClientResponse clientResponse = webClient.post()
                                                 .uri("/accounts")
                                                 .contentType(MediaType.APPLICATION_JSON)
                                                 .bodyValue(account)
                                                 .exchange()
                                                 .block();

        clientResponse = webClient.post()
                                  .uri("/accounts")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .bodyValue(account)
                                  .exchange()
                                  .block();

        assertEquals(HttpStatus.CONFLICT, clientResponse.statusCode());
    }

    @Test
    public void addAndDeleteBeneficiary_WebClient_blocking() throws Exception {
        // perform both add and delete to avoid issues with side effects
        String addUrl = "/accounts/{accountId}/beneficiaries";

        ClientResponse clientResponse = webClient.post()
                                                 .uri(addUrl, 1)
                                                 .contentType(MediaType.APPLICATION_JSON)
                                                 .bodyValue("David")
                                                 .exchange()
                                                 .block();

        URI newBeneficiaryLocation = new URI(clientResponse.headers().header("Location").get(0));

        Beneficiary newBeneficiary= webClient.get()
                                            .uri(newBeneficiaryLocation)
                                            .accept(MediaType.APPLICATION_JSON)
                                            .retrieve()
                                            .bodyToMono(Beneficiary.class)
                                            .block();

        assertEquals("David", newBeneficiary.getName());

        clientResponse = webClient.delete()
                 .uri(newBeneficiaryLocation)
                 .exchange()
                 .block();

        clientResponse = webClient.get()
                 .uri(newBeneficiaryLocation)
                 .accept(MediaType.APPLICATION_JSON)
                 .exchange()
                 .block();

        assertEquals(HttpStatus.NOT_FOUND, clientResponse.statusCode());
    }

}
