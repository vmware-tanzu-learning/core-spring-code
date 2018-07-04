package dto;

import java.util.HashSet;
import java.util.Set;

import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import common.money.Percentage;

/**
 * An account for a member of the reward network. An account has one or more
 * beneficiaries whose allocations must add up to 100%.
 * 
 * An account can make contributions to its beneficiaries. Each contribution is
 * distributed among the beneficiaries based on an allocation.
 * 
 * A Data Transfer Object for account. An aggregate.
 */
public class AccountDTO {

	boolean isModified = false;
	
	Account account;

	Set<BeneficiaryDTO> beneficiaries;

	// protected AccountDTO() {
	// }
	//
	// /**
	// * Create a new account.
	// * @param number the account number
	// * @param name the name on the account
	// */
	// public AccountDTO(String number, String name) {
	// this.number = number;
	// this.name = name;
	// }

	public AccountDTO(Account account) {
		this.account = account;

		// Deep copy the beneficiaries - avoids any Hibernate lazy loading
		// errors
		this.beneficiaries = new HashSet<BeneficiaryDTO>();
		for (Beneficiary b : account
				.getBeneficiaries()) {
			this.beneficiaries.add(new BeneficiaryDTO(b.getName(), b
					.getAllocationPercentage(), b.getSavings()));
		}
	}

	/**
	 * Returns the entity identifier used to internally distinguish this entity
	 * among other entities of the same type in the system. Should typically
	 * only be called by privileged data access infrastructure code such as an
	 * Object Relational Mapper (ORM) and not by application code.
	 * 
	 * @return the internal entity identifier
	 */
	public Integer getEntityId() {
		return account.getEntityId().intValue();
	}

	/**
	 * Sets the internal entity identifier - should only be called by privileged
	 * data access code (repositories that work with an Object Relational Mapper
	 * (ORM)). Should never be set by application code explicitly.
	 * 
	 * @param entityId
	 *            the internal entity identifier
	 */
	// public void setEntityId(Integer entityId) {
	// this.entityId = entityId;
	// }

	/**
	 * Returns the number used to uniquely identify this account.
	 */
	public String getNumber() {
		return account.getNumber();
	}

	/**
	 * Sets the number used to uniquely identify this account.
	 * 
	 * @param number
	 *            The number for this account
	 */
	public void setNumber(String number) {
		account.setNumber(number);
		isModified = true;
	}

	/**
	 * Returns the name on file for this account.
	 */
	public String getName() {
		return account.getName();
	}

	/**
	 * Sets the name on file for this account.
	 * 
	 * @param name
	 *            The name for this account
	 */
	public void setName(String name) {
		account.setName(name);
		isModified = true;
	}

	/**
	 * Add a single beneficiary with a 100% allocation percentage.
	 * 
	 * @param beneficiaryName
	 *            the name of the beneficiary (should be unique)
	 */
	public void addBeneficiary(String beneficiaryName) {
		addBeneficiary(beneficiaryName, Percentage.oneHundred());
		isModified = true;
	}

	/**
	 * Add a single beneficiary with the specified allocation percentage.
	 * 
	 * @param beneficiaryName
	 *            the name of the beneficiary (should be unique)
	 * @param allocationPercentage
	 *            the beneficiary's allocation percentage within this account
	 */
	public void addBeneficiary(String beneficiaryName,
			Percentage allocationPercentage) {
		account.addBeneficiary(beneficiaryName, allocationPercentage);
	}

	/**
	 * Returns the beneficiaries for this account.
	 * <p>
	 * Callers should not attempt to hold on or modify the returned set. This
	 * method should only be used transitively; for example, called to
	 * facilitate account reporting.
	 * 
	 * @return the beneficiaries of this account
	 */
	public Set<BeneficiaryDTO> getBeneficiaries() {
		return beneficiaries;
	}

	/**
	 * Returns a single account beneficiary. Callers should not attempt to hold
	 * on or modify the returned object. This method should only be used
	 * transitively; for example, called to facilitate reporting or testing.
	 * 
	 * @param name
	 *            the name of the beneficiary e.g "Annabelle"
	 * @return the beneficiary object
	 */
	public BeneficiaryDTO getBeneficiary(String name) {
		for (BeneficiaryDTO b : beneficiaries) {
			if (b.getName().equals(name)) {
				return b;
			}
		}
		throw new IllegalArgumentException("No such beneficiary with name '"
				+ name + "'");
	}

	/**
	 * Removes a single beneficiary from this account.
	 * 
	 * @param beneficiaryName
	 *            the name of the beneficiary (should be unique)
	 */
	public void removeBeneficiary(String beneficiaryName) {
		beneficiaries.remove(getBeneficiary(beneficiaryName));
		isModified = true;
	}

	public String toString() {
		return "Number = '" + getNumber() + "', name = " + getNumber()
				+ "', beneficiaries = " + beneficiaries;
	}
}