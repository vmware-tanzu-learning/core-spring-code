package accounts.web;

// TODO-21: Add Spring Boot security starter to the pom.xml or build.gradle
// - You might want to refresh the IDE so that it picks up the change in the build file
//
// TODO-22: Uncomment code below until there is no compile error

//@Configuration
//@EnableWebSecurity
public class ActuatorSecurityConfiguration /* extends WebSecurityConfigurerAdapter */ {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        auth.inMemoryAuthentication()
//            .withUser("actuator").password(passwordEncoder.encode("actuator")).roles("ACTUATOR").and()
//            .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN", "ACTUATOR");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        // TODO-23: Configure access control to actuator endpoints as following
//        // - Anybody can access "health" and "info" endpoints
//        // - ADMIN role can access "conditions" endpoint
//        // - ACTUATOR role can access all the other endpoints
//
//        // @formatter:off
//        http.authorizeRequests()
//                .requestMatchers(/* Add code here */).permitAll()
//                .requestMatchers(/* Add code here */).hasRole("ADMIN")
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
//                .anyRequest().authenticated()
//                .and()
//            .httpBasic()
//                 .and()
//            .csrf().disable();
//        // @formatter:on
//
//    }
}
