package rewards;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import config.RewardsConfig;

// TODO-08: Get Spring Boot to populate the database - see application.properties
// TODO-09: Get Hibernate to show its SQL nicely - see application.properties
// TODO-10: Stop Hibernate from populating the database - see application.properties
//
// TODO-11: Add an annotation to THIS class that tells JPA where to find entities.
//          Specify the same package that setPackagesToScan is using (below).
//
// TODO-12: Delete all three bean methods as we don't need them any more.
//          Spring Boot will do it all for us.
//
// TODO-13: Annotate to enable Spring Boot auto-configuration.
//
// TODO-14: Run the test. The test still should pass.
@Configuration
@Import(RewardsConfig.class)
public class SystemTestConfig {
	
	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
	@Bean
	public DataSource dataSource(){
		return
			(new EmbeddedDatabaseBuilder())
			.addScript("classpath:rewards/testdb/schema.sql")
			.addScript("classpath:rewards/testdb/data.sql")
			.build();
	}	
	
	
	/**	
	 * LocalContainerEntityManagerFactoryBean. 
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.HSQL);

		Properties props = new Properties();
		props.setProperty("hibernate.format_sql", "true");
		
		LocalContainerEntityManagerFactoryBean emfb = 
			new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource());
		emfb.setPackagesToScan("rewards.internal");
		emfb.setJpaProperties(props);
		emfb.setJpaVendorAdapter(adapter);
		
		return emfb;
	}


	/**	
	 * JpaTransactionManager. 
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
		
}
