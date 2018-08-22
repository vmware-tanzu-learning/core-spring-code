package rewards;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jndi")
public class TestInfrastructureJndiConfig {

	/**
	 * Static method because we are defining a Bean post-processor.
	 */
	@Bean
	public static SimpleJndiHelper jndiHelper(){
		return new SimpleJndiHelper();
	}
	
	@Bean
	public DataSource dataSource() throws Exception {
		return (DataSource) 
			(new InitialContext())
				.lookup("java:/comp/env/jdbc/rewards");	
	}
}
