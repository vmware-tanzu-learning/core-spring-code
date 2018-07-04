package rewards.internal.restaurant;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Manually configured integration test for the JPA based restaurant repository
 * implementation. Tests repository behavior and verifies the Restaurant JPA
 * mapping is correct.
 */
public class JpaRestaurantRepositoryTests extends
		AbstractRestaurantRepositoryTests {

	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		restaurantRepository = new JpaRestaurantRepository(entityManager);

		// begin a transaction
		transactionManager = new JpaTransactionManager(entityManagerFactory);
		transactionStatus = transactionManager
				.getTransaction(new DefaultTransactionDefinition());
	}

	@Test
	@Override
	public void testProfile() {
		Assert.assertTrue("JPA expected",
				restaurantRepository instanceof JpaRestaurantRepository);
	}

	@After
	public void tearDown() throws Exception {
		// rollback the transaction to avoid corrupting other tests
		if (transactionManager != null)
			transactionManager.rollback(transactionStatus);
	}

	private EntityManagerFactory createEntityManagerFactory() throws Exception {
		// create a FactoryBean to help create a Jpa SessionFactory
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(createTestDataSource());
		factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factoryBean.setJpaProperties(createJpaProperties());

		// Not using persistence unit or persistence.xml, so need to tell
		// JPA where to find Entities
		factoryBean.setPackagesToScan("rewards.internal");

		// initialize according to the Spring InitializingBean contract
		factoryBean.afterPropertiesSet();
		// get the created session factory
		return (EntityManagerFactory) factoryBean.getObject();
	}

	private Properties createJpaProperties() {
		Properties properties = new Properties();
		// turn on formatted SQL logging (very useful to verify JPA is
		// issuing proper SQL)
		properties.setProperty("hibenate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder().setName("rewards")
				.addScript("/rewards/testdb/schema.sql")
				.addScript("/rewards/testdb/data.sql").build();
	}
}
