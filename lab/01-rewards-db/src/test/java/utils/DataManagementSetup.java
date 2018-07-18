package utils;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Setup a JPA data-management layer without using Spring (for testing).
 */
public class DataManagementSetup {

	public static final String DOMAIN_OBJECTS_PARENT_PACKAGE = "rewards.internal";

	private DataSource dataSource;
	private EntityManagerFactory entityManagerFactory;
	private PlatformTransactionManager transactionManager;

	public DataManagementSetup() {
	}

	private void setup() {
		if (dataSource == null) {
			dataSource = createTestDataSource();
			entityManagerFactory = createEntityManagerFactory();
			transactionManager = createTransactionManager();
		}
	}

	public DataSource getDataSource() {
		setup();
		return dataSource;
	}

	public EntityManager createEntityManager() {
		setup();
		return entityManagerFactory.createEntityManager();
	}

	public PlatformTransactionManager getTransactionManager() {
		setup();
		return transactionManager;
	}

	// - - - - - - - - - - - - - - - INTERNAL METHODS - - - - - - - - - - - - - - -

	protected DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder().setName("rewards") //
				.addScript("/rewards/testdb/schema.sql") //
				.addScript("/rewards/testdb/data.sql") //
				.build();
	}

	protected JpaTransactionManager createTransactionManager() {
		return new JpaTransactionManager(entityManagerFactory);
	}

	protected JpaVendorAdapter createVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.HSQL);
		return adapter;
	}

	protected Properties createJpaProperties() {
		Properties properties = new Properties();
		// turn on formatted SQL logging (very useful to verify Jpa is
		// issuing proper SQL)
		properties.setProperty("hibenate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}

	protected final EntityManagerFactory createEntityManagerFactory() {

		// Create a FactoryBean to help create a JPA EntityManagerFactory
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setJpaVendorAdapter(createVendorAdapter());
		factoryBean.setJpaProperties(createJpaProperties());

		// Not using persistence unit or persistence.xml, so need to tell
		// JPA where to find Entities
		factoryBean.setPackagesToScan(DOMAIN_OBJECTS_PARENT_PACKAGE);

		// initialize according to the Spring InitializingBean contract
		factoryBean.afterPropertiesSet();

		// get the created session factory
		return (EntityManagerFactory) factoryBean.getObject();
	}

}
