package config;

import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration class for Persistence-specific objects, including profile
 * choices for JPA via Hibernate or JPA via EclipseLink. Only used by tests in
 * this class (since Spring Boot cannot be assumed).
 * <p>
 * To simulate Spring Boot we load <code>application.properties</code> manually,
 * if it exists, and mimic Boot's <code>spring.jpa.show-sql</code> property.
 */
@Configuration
@PropertySource(value = "application.properties", ignoreResourceNotFound = true)
public class DbConfig {

	public static final String DOMAIN_OBJECTS_PARENT_PACKAGE = "rewards.internal";

	@Value("${spring.jpa.show-sql:true}")  // Default to true if not set elsewhere
	private String showSql;

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		return (new EmbeddedDatabaseBuilder()) //
				.addScript("classpath:rewards/testdb/schema.sql") //
				.addScript("classpath:rewards/testdb/data.sql").build();
	}

	/**
	 * Transaction Manager For JPA
	 */
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new JpaTransactionManager();
	}

	/**
	 * Create an EntityManagerFactoryBean.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter adapter) {

		// Tell the underlying implementation what type of database we are using - a
		// hint to generate better SQL
		if (adapter instanceof AbstractJpaVendorAdapter) {
			((AbstractJpaVendorAdapter) adapter).setDatabase(Database.HSQL);
		}

		// Setup configuration properties
		Properties props = new Properties();
		boolean showSql = "TRUE".equalsIgnoreCase(this.showSql);
		Logger.getLogger("config").info("JPA Show generated SQL? " + this.showSql);

		if (adapter instanceof EclipseLinkJpaVendorAdapter) {
			props.setProperty("eclipselink.logging.level", showSql ? "FINE" : "WARN");
			props.setProperty("eclipselink.logging.parameters", String.valueOf(showSql));
			props.setProperty("eclipselink.weaving", "false");
		} else {
			props.setProperty("hibernate.show_sql", String.valueOf(showSql));
			props.setProperty("hibernate.format_sql", "true");
		}

		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setPackagesToScan(DOMAIN_OBJECTS_PARENT_PACKAGE);
		emfb.setJpaProperties(props);
		emfb.setJpaVendorAdapter(adapter);
		emfb.setDataSource(dataSource());

		return emfb;
	}

	@Bean
	@Profile("!jpa-elink") // Default is JPA using Hibernate
	JpaVendorAdapter hibernateVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	@Profile("jpa-elink") // Explicitly request JPA using EclipseLink
	JpaVendorAdapter eclipseLinkVendorAdapter() {
		return new EclipseLinkJpaVendorAdapter();
	}

}
