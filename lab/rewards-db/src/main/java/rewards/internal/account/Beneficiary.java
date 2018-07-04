package rewards.internal.account;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import common.money.MonetaryAmount;
import common.money.Percentage;

/**
 * A single beneficiary allocated to an account. Each beneficiary has a name
 * (e.g. Mary), an allocation percentage and a savings balance tracking how much
 * money has been saved for them to date (e.g. $1000).
 */
@Entity
@Table(name = "T_ACCOUNT_BENEFICIARY")
public class Beneficiary {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entityId;

	@Column(name = "NAME")
	private String name;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "ALLOCATION_PERCENTAGE"))
	private Percentage allocationPercentage;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "SAVINGS"))
	private MonetaryAmount savings = MonetaryAmount.zero();

	protected Beneficiary() {
	}

	/**
	 * Creates a new account beneficiary.
	 * 
	 * @param name
	 *            the name of the beneficiary
	 * @param allocationPercentage
	 *            the beneficiary's allocation percentage within its account
	 */
	public Beneficiary(String name, Percentage allocationPercentage) {
		this.name = name;
		this.allocationPercentage = allocationPercentage;
	}

	/**
	 * Creates a new account beneficiary. This constructor should be called by
	 * privileged objects responsible for reconstituting an existing Account
	 * object from some external form such as a collection of database records.
	 * Marked package-private to indicate this constructor should never be
	 * called by general application code.
	 * 
	 * @param name
	 *            the name of the beneficiary
	 * @param allocationPercentage
	 *            the beneficiary's allocation percentage within its account
	 * @param savings
	 *            the total amount saved to-date for this beneficiary
	 */
	Beneficiary(String name, Percentage allocationPercentage,
			MonetaryAmount savings) {
		this.name = name;
		this.allocationPercentage = allocationPercentage;
		this.savings = savings;
	}

	/**
	 * Returns the entity identifier used to internally distinguish this entity
	 * among other entities of the same type in the system. Should typically
	 * only be called by privileged data access infrastructure code such as an
	 * Object Relational Mapper (ORM) and not by application code.
	 * 
	 * @return the internal entity identifier
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the internal entity identifier - should only be called by privileged
	 * data access code (repositories that work with an Object Relational Mapper
	 * (ORM)). Should never be set by application code explicitly.
	 * 
	 * @param entityId
	 *            the internal entity identifier
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
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
	 * Sets the beneficiary's allocation percentage in this account.
	 * 
	 * @param allocationPercentage
	 *            The new allocation percentage
	 */
	public void setAllocationPercentage(Percentage allocationPercentage) {
		this.allocationPercentage = allocationPercentage;
	}

	/**
	 * Returns the amount of savings this beneficiary has accrued.
	 */
	public MonetaryAmount getSavings() {
		return savings;
	}

	/**
	 * Credit the amount to this beneficiary's saving balance.
	 * 
	 * @param amount
	 *            the amount to credit
	 */
	public void credit(MonetaryAmount amount) {
		savings = savings.add(amount);
	}

	public String toString() {
		return "name = '" + name + "' (" + entityId + "), allocationPercentage = "
				+ allocationPercentage + ", savings = " + savings;
	}
}