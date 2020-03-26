package accounts.client;

import common.money.Percentage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountWebTestClientTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebTestClient webTestClient;

    private Random random = new Random();

    @Test
    public void listAccounts_WebTestClient() {
        String url = "/accounts";

        webTestClient.get().uri(url)
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk()
                     .expectHeader().contentType(MediaType.APPLICATION_JSON)
                     .expectBody(Account[].class)
                     .consumeWith(response -> {
                         Account[] accounts = response.getResponseBody();
                         assertThat(accounts.length >= 21).isTrue();
                         assertThat(accounts[0].getName()).isEqualTo("Keith and Keri Donald");
                         assertThat(accounts[0].getBeneficiaries().size()).isEqualTo(2);
                         assertThat(accounts[0].getBeneficiary("Annabelle").getAllocationPercentage()).isEqualTo(Percentage.valueOf("50%"));
                     });
    }

    @Test
    public void getAccount_WebTestClient() {
        String url = "/accounts/{accountId}";

        webTestClient.get()
                     .uri(url, 0)
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody(Account.class)
                     .consumeWith(response -> {
                         Account account = response.getResponseBody();
                         assertThat(account.getName()).isEqualTo("Keith and Keri Donald");
                         assertThat(account.getBeneficiaries().size()).isEqualTo(2);
                         assertThat(account.getBeneficiary("Annabelle").getAllocationPercentage()).isEqualTo(Percentage.valueOf("50%"));
                     });

    }

    @Test
    public void createAccount_WebTestClient() {
        String url = "/accounts";
        // use a unique number to avoid conflicts
        String number = String.format("12345%4d", random.nextInt(10000));
        Account account = new Account(number, "John Doe");
        account.addBeneficiary("Jane Doe");

        webTestClient.post()
                     .uri(url)
                     .contentType(MediaType.APPLICATION_JSON)
                     .accept(MediaType.APPLICATION_JSON)
                     .body(Mono.just(account), Account.class)
                     .exchange()
                     .expectStatus().isCreated()
                     .expectHeader().value("Location", location -> {
            try {
                URI newAccountLocation = new URI(location);
                webTestClient.get()
                             .uri(newAccountLocation)
                             .exchange()
                             .expectStatus().isOk()
                             .expectBody(Account.class)
                             .consumeWith(response -> {
                                 Account retrievedAccount = response.getResponseBody();
                                 assertThat(retrievedAccount.getNumber()).isEqualTo(account.getNumber());

                                 Beneficiary accountBeneficiary = account.getBeneficiaries().iterator().next();
                                 Beneficiary retrievedAccountBeneficiary = retrievedAccount.getBeneficiaries().iterator().next();

                                 assertThat(retrievedAccountBeneficiary.getName()).isEqualTo(accountBeneficiary.getName());
                                 assertThat(retrievedAccount.getEntityId()).isNotNull();
                             });
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

    }

	@Test
	public void addAndDeleteBeneficiary_WebTestClient() {

		String addUrl = "/accounts/{accountId}/beneficiaries";

		webTestClient.post().uri(addUrl, 1)
					 .contentType(MediaType.APPLICATION_JSON)
					 .accept(MediaType.APPLICATION_JSON)
					 .body(Mono.just("David"), String.class)
					 .exchange()
					 .expectStatus().isCreated()
					 .expectHeader().value("Location", location -> {
			try {
				URI newBeneficiaryLocation = new URI(location);
				webTestClient.get()
							 .uri(newBeneficiaryLocation)
							 .exchange()
							 .expectStatus().isOk()
							 .expectBody(Beneficiary.class)
							 .consumeWith(response -> {
								 Beneficiary newBeneficiary = response.getResponseBody();
								 assertThat(newBeneficiary.getName()).isEqualTo("David");
							 });

				webTestClient.delete()
							 .uri(newBeneficiaryLocation)
							 .exchange()
							 .expectStatus().isNoContent();

				webTestClient.get()
							 .uri(newBeneficiaryLocation)
							 .exchange()
							 .expectStatus().isNotFound();

			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		});

	}

}
