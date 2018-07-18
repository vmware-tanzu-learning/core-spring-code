package config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.samskivert.mustache.Mustache.Compiler;

import accounts.AccountManager;
import accounts.internal.JpaAccountManager;

/**
 * Imports Rewards application from rewards-db project.
 */
@Configuration
@Import({AppConfig.class,DbConfig.class})
@EnableTransactionManagement
public class RootConfig {

	/**
	 * A new service has been created for accessing Account information.
	 * 
	 * @return The new account-manager instance.
	 */
	@Bean
	public AccountManager accountManager() {
		return new JpaAccountManager();
	}
	
	/**
	 * Default Mustache view-resolver. Has no prefix or suffix, so you have to
	 * provide full path to HTML templates.
	 * 
	 * @param mustacheCompiler
	 * @return
	 */

	// TODO-07b: Entering the full path of templates is tedious. This view-resolver
	//           needs configuring. But we will let Spring Boot do it all, so comment
	//           out this bean, then look for TODO-07c in application.properties.
	//
	@Bean
	public MustacheViewResolver mustacheViewResolver(Compiler mustacheCompiler) {
		MustacheViewResolver resolver = new MustacheViewResolver(mustacheCompiler);
		resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
		return resolver;
	}
}
