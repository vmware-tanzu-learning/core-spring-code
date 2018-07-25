package accounts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import accounts.AccountManager;
import common.money.Percentage;
import rewards.internal.account.Account;
import rewards.internal.account.AccountRepository;

@Service
public class AccountManagerImpl implements AccountManager {

	public AccountRepository accountRepository;

	public AccountManagerImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public String getInfo() {
		return "Uses JDBC Implementation";
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.getAllAccounts();
	}

	@Override
	public Account getAccount(Long id) {
		return accountRepository.getAccount(id);
	}

	@Override
	public Account save(Account account) {
		throw new UnsupportedOperationException("save");
	}

	@Override
	public void update(Account account) {
		throw new UnsupportedOperationException("update");
	}

	@Override
	public void updateBeneficiaryAllocationPercentages(Long accountId, Map<String, Percentage> allocationPercentages) {
		throw new UnsupportedOperationException("updateBeneficiaryAllocationPercentages");
	}

	@Override
	public void addBeneficiary(Long accountId, String beneficiaryName) {
		throw new UnsupportedOperationException("addBeneficiary");
	}

	@Override
	public void removeBeneficiary(Long accountId, String beneficiaryName,
			Map<String, Percentage> allocationPercentages) {
		throw new UnsupportedOperationException("removeBeneficiary");
	}

}
