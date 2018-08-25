package rewards.internal.account;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import common.money.MonetaryAmount;
import common.money.Percentage;

/**
 * A single beneficiary allocated to an account. Each beneficiary has a name (e.g. Annabelle) and a savings balance
 * tracking how much money has been saved for he or she to date (e.g. $1000).
 */
//	TODO-04: Review the JPA annotations on this class and make sure you know what each does.
//	@AttributeOverride - Tells JPA to use the ALLOCATION_PERCENTAGE column on T_ACCOUNT_BENEFICIARY 
//                       to populate Percentage.value.
@Entity
@Table(name="T_ACCOUNT_BENEFICIARY")
public class Beneficiary {
    @Id
    @Column(name="ID")
	private Long entityId;

    // No need for @Column, mapped automatically to NAME
	private String name;

	@AttributeOverride(name="value",column=@Column(name="ALLOCATION_PERCENTAGE"))
	private Percentage allocationPercentage;

	@AttributeOverride(name="value",column=@Column(name="SAVINGS"))
	private MonetaryAmount savings = MonetaryAmount.zero();

	public Beneficiary() {
	}

	/**
	 * Creates a new account beneficiary.
	 * @param name the name of the beneficiary
	 * @param allocationPercentage the beneficiary's allocation percentage within its account
	 */
	public Beneficiary(String name, Percentage allocationPercentage) {
		this.name = name;
		this.allocationPercentage = allocationPercentage;
	}

	/**
	 * Creates a new account beneficiary. This constructor should be called by privileged objects responsible for
	 * reconstituting an existing Account object from some external form such as a collection of database records.
	 * Marked package-private to indicate this constructor should never be called by general application code.
	 * @param name the name of the beneficiary
	 * @param allocationPercentage the beneficiary's allocation percentage within its account
	 * @param savings the total amount saved to-date for this beneficiary
	 */
	Beneficiary(String name, Percentage allocationPercentage, MonetaryAmount savings) {
		this.name = name;
		this.allocationPercentage = allocationPercentage;
		this.savings = savings;
	}

	/**
	 * Returns the beneficiary name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the beneficiary's allocation percentage in this account.
	 */
	public Percentage getAllocationPercentage() {
		return allocationPercentage;
	}

	/**
	 * Returns the amount of savings this beneficiary has accrued.
	 */
	public MonetaryAmount getSavings() {
		return savings;
	}
	
	/** 
	 * Returns the id for this beneficiary.
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Credit the amount to this beneficiary's saving balance.
	 * @param amount the amount to credit
	 */
	public void credit(MonetaryAmount amount) {
		savings = savings.add(amount);
	}

	public String toString() {
		return "name = '" + name + "', allocationPercentage = " + allocationPercentage + ", savings = " + savings + ")";
	}
}