package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;

import rewards.internal.aspects.RepositoryPerformanceMonitor;
import rewards.internal.monitor.jamon.JamonMonitorFactory;

@Configuration
@EnableAspectJAutoProxy
@EnableMBeanExport
public class AspectsConfig {

	@Bean
	public JamonMonitorFactory monitorFactory() {
		return new JamonMonitorFactory();
	}
	
	@Bean
	public RepositoryPerformanceMonitor repositoryPerformanceMonitor() {
		return new RepositoryPerformanceMonitor(monitorFactory());
	}
	

}
