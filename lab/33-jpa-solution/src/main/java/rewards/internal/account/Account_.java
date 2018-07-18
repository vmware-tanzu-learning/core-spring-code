package rewards.internal.account;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * This is the meta-model data for the class Account.  It can also be generated
 * automatically by the JPA implementation.  This class is only used by the Bonus
 * Section and can be ignored.
 */
@StaticMetamodel(Account.class)
public class Account_ {

    public static volatile SingularAttribute<Account, Long> entityId;

    public static volatile SingularAttribute<Account, String> number;

    public static volatile SingularAttribute<Account, String> name;

    public static volatile SetAttribute<Account, Beneficiary> beneficiaries;

    public static volatile SingularAttribute<Account, String> creditCardNumber;

}
