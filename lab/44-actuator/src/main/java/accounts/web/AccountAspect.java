package accounts.web;

/*
 * TODO-26 (Optional): Use AOP for counting logic
 * - Add `spring-boot-starter-aop` starter to the `pom.xml` or the
 *   `build.gradle`
 * - Make this class an aspect, through which `account.fetch` counter,
 *   which has a tag of `type`/`fromAspect` key/value pair, gets incremented
 *   every time `accountSummary` method of the `AccountController` class
 *   is invoked
 * - Access `/accounts` several times and verify the metrics of
 *   `/actuator/metrics/account.fetch?tag=type:fromAspect
 */
public class AccountAspect {
}
