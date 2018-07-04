package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import rewards.internal.aspects.RepositoryPerformanceMonitor;
import rewards.internal.monitor.jamon.JamonMonitorFactory;

@Configuration
@EnableAspectJAutoProxy

//	TODO-03: Activate annotation driven JMX. 

//	TODO-04: Specify -Dcom.sun.management.jmxremote as a JVM argument.
//  See detailed lab notes if you don't know how to do this.

//  TODO-05: Run the application again (Run As ... Spring Boot App)
//           Check the home page to see it is running OK
//           http://localhost:8080

//	TODO-06: View the statistics using JConsole.
//  Ask your trainer if you aren't sure about using JConsole.
//  1. Find you MBean in JConsole and look at its attributes
//  2. Create some new rewards in the browser
//  3. Refresh the MBean attributes, how have they changed?
//  4. How do the attributes change when a) you create a new reward
//     or b) if you try to create a bogus award?
//  5. As this is a Spring Boot, it also offers an MBean. Open up
//     Endpoint and you will see the "Actuators".  Most have an
//     Operations attribute returning information as a Map.
//  
//  IMPORTANT NOTE: If JConsole is unable to connect to your application,
//  refer to the Tip in the detailed notes about using JVM -D flags (the
//  section titled "View the monitor statistics using JConsole").  You
//  should now be able to connect using the "Remote" option.

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
