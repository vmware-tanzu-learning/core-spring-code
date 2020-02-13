package accounts.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.support.SimpleSessionStatus;

import accounts.AccountManager;
import rewards.internal.account.Account;

/**
 * A JUnit test case testing the AccountController.
 * <p>
 * This is an abstract class so the we can run these tests as either a unit test
 * or an integration test, depending on the configuration.
 */
public abstract class AbstractAccountControllerTests {

	private static final long VALID_ACCOUNT_ID = 0L;
	private static final long ILLEGAL_ACCOUNT_ID = 10101;

	@Autowired
	protected AccountController controller;

	@Autowired
	protected AccountManager manager;

	@Test
	public void getAccountDetails() {
		ExtendedModelMap model = new ExtendedModelMap();
		controller.getAccountDetails(VALID_ACCOUNT_ID, model, null);
		Account account = (Account) model.get("account");
		assertNotNull(account);
		assertEquals(Long.valueOf(0), account.getEntityId());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAccountList() {
		ExtendedModelMap model = new ExtendedModelMap();
		controller.getAccountList(model, null);
		List<Account> accounts = (List<Account>) model.get("accounts");
		assertNotNull(accounts);
		assertEquals(getNumAccountsExpected(), accounts.size());
		assertEquals(Long.valueOf(0), accounts.get(0).getEntityId());
	}

	@Test
	public void invalidId() {
		assertThrows(ObjectRetrievalFailureException.class, () -> {
			ExtendedModelMap model = new ExtendedModelMap();
			controller.getAccountDetails(ILLEGAL_ACCOUNT_ID, model, null);
		});
	}

	@Test
	public void validateAllValid() {
		Account account = new Account("1", "Ben");
		Errors errors = new BindException(account, "account");
		controller.validateAccount(account, errors);
		assertEquals(0, errors.getErrorCount(), "No errors should be registered");
	}

	@Test
	public void validateInvalidName() {
		Account account = new Account("1", "");
		Errors errors = new BindException(account, "account");
		controller.validateAccount(account, errors);
		assertEquals(1, errors.getErrorCount(), "One error should be registered");
		FieldError error = errors.getFieldError("name");
		assertNotNull(error, "Should have an error registred for the name field");
		assertEquals("empty.value", error.getCode(), "Should have registered an empty value error");
	}

	@Test
	public void validateInvalidNumber() {
		Account account = new Account("", "Ben");
		Errors errors = new BindException(account, "account");
		controller.validateAccount(account, errors);
		assertEquals(1, errors.getErrorCount(), "One error should be registered");
		FieldError error = errors.getFieldError("number");
		assertNotNull(error, "Should have an error registred for the number field");
		assertEquals("empty.value", error.getCode(), "Should have registered an empty value error");
	}

	@Test
	public void validateAllInvalid() {
		Account account = new Account(null, null);
		Errors errors = new BindException(account, "account");
		controller.validateAccount(account, errors);
		assertEquals(2, errors.getErrorCount(), "Two errors should be registered");
	}

	@Test
	public void editAccount() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		controller.getEditAccount(VALID_ACCOUNT_ID, model, null);
		Account account = (Account) model.get("account");
		assertNotNull(account);
		assertEquals(Long.valueOf(0), account.getEntityId());
	}

	@Test
	public void successfulPost() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		controller.getEditAccount(VALID_ACCOUNT_ID, model, null);
		Account account = (Account) model.get("account");
		account.setName("Ben");
		account.setNumber("987654321");
		BindingResult br = new MapBindingResult(model, "account");
		String viewName = controller.postEditAccount(account, br, new SimpleSessionStatus());
		Account modifiedAccount = manager.getAccount(VALID_ACCOUNT_ID);
		assertEquals("Ben", modifiedAccount.getName(), "Object name has not been updated by post");
		assertEquals("987654321", modifiedAccount.getNumber(), "Object number has not been updated by post");
		assertEquals("redirect:/accounts/accountDetails?entityId=0", viewName,
				"Post has not redirected to the correct URL");
	}

	@Test
	public void unsuccessfulPost() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		controller.getEditAccount(VALID_ACCOUNT_ID, model, null);
		Account account = (Account) model.get("account");
		account.setName("");
		account.setNumber("");
		BindingResult br = new MapBindingResult(model, "account");
		String viewName = controller.postEditAccount(account, br, new SimpleSessionStatus());
		assertEquals("editAccount", viewName, "Invalid Post has not returned to correct view");
	}

	/**
	 * How many accounts are defined for use in the test?
	 * 
	 * @return Number of accounts available.
	 */
	protected abstract int getNumAccountsExpected();

}
