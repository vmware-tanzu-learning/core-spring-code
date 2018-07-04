package config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jpa")
public class JpaStatisticsConfig {

	@Bean
	public Statistics jpaStatistics(EntityManagerFactory emf) throws Exception {
		SessionFactory sessionFactory = ((HibernateEntityManagerFactory) emf).getSessionFactory();
		return sessionFactory.getStatistics();
	}

}
