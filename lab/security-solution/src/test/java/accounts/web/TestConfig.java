package accounts.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import config.AppConfig;
import config.DbConfig;

@Configuration
@ComponentScan("accounts.web")
@Import({AppConfig.class,DbConfig.class})
@EnableTransactionManagement
public class TestConfig {

}
