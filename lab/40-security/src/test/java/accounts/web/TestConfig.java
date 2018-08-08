package accounts.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.RootConfig;

@Configuration
@ComponentScan("accounts.web")
@Import(RootConfig.class)
public class TestConfig {

}
