package accounts.web;

import accounts.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import rewards.internal.account.Account;

import java.security.Principal;

/**
 * A Spring MVC @Controller controller handling requests to view and modify
 * Account information.
 *
 * Note that all the Account application classes are imported from the
 * rewards-db project:
 *
 * No repository layer is being used - the account-manager does
 * everything
 */
@Controller
@RequestMapping("/accounts")
@SessionAttributes("account")
public class AccountController {
	private AccountManager accountManager;

	@Autowired
	public AccountController(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@RequestMapping("/accountList")
	public String getAccountList(Model model, Principal principal) {
		model.addAttribute("accounts", accountManager.getAllAccounts());
		return "accountList";
	}

	@RequestMapping("/accountDetails")
	public String getAccountDetails(Long entityId, Model model, Principal principal) {
		Account account = accountManager.getAccount(entityId);
		if(account == null) {
			throw new ObjectRetrievalFailureException(Account.class, entityId);
		}
		model.addAttribute("account", account);
		return "accountDetails";
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	// The next 4 methods enable form handling so that accounts can
	// be edited. This, and much more, is covered by the Spring-Web course.
	//
	// You do NOT need to understand the following to do this lab.
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setRequiredFields(new String[] { "number", "name" });
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editAccount")
	public String getEditAccount(Long entityId, Model model, Principal principal) {
		model.addAttribute("account", accountManager.getAccount(entityId));
		return "editAccount";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editAccount")
	public String postEditAccount(Account account, BindingResult bindingResult, SessionStatus status) {
		validateAccount(account, bindingResult);
		if (bindingResult.hasErrors()) {
			return "editAccount";
		}
		accountManager.update(account);
		status.setComplete();
		return "redirect:/accounts/accountDetails?entityId="
                + account.getEntityId();
	}

	public void validateAccount(Account account, Errors errors) {
		if (!StringUtils.hasText(account.getNumber())) {
			errors.rejectValue("number", "empty.value");
		}

		if (!StringUtils.hasText(account.getName())) {
			errors.rejectValue("name", "empty.value");
		}
	}

}
