package rewards.internal.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import accounts.AccountsApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountsApplication.class)
public class JdbcAccountRepositoryTests {

	@Autowired
	private JdbcAccountRepository repo;

	@Test
	public void getAllAccounts() {
		List<Account> result = repo.getAllAccounts();
		assertEquals(21, result.size());
	}

	@Test
	public void getAccount() {
		Account account = repo.getAccount(0L);
		assertEquals(0L, (long) account.getEntityId());
	}

	@Test
	public void findByCreditCard() {
		Account account = repo.findByCreditCard("1234123412341234");
		assertEquals(0L, (long) account.getEntityId());
	}

	@Test
	public void updateBeneficiaries() {

	}

}
