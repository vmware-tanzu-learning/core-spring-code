package accounts.web;

import org.springframework.context.annotation.Configuration;

// TODO-21: Add Spring boot security starter to pom.xml or build.gradle
//          (Look for TO-DO-21 in the pom.xml or build.gradle)

// TODO-22: Add security configuration
// - Make this class a configuration class
//   1. Add @EnableWebSecurity annotation to the class
//   2. Extend WebSecurityConfigurerAdapter
// - Add two users to in-memory identity store using DelegatingPasswordEncoder
//   1. actuator/actuator with ACTUATOR role
//   2. admin/admin with ADMIN and ACTUATOR role
// - Add access control configuration
//   1. Anybody can access "health" and "info" endpoints without logging in
//   2. Only "admin" role can access "conditions" endpoint
//   3. Only 'admin" or "actuator" role can access all the other endpoints

// TODO-23: Access the endpoints with security
// - Make sure application is restarted
// - Open a new Chrome Incognito browser
// - Access "health" and "info" endpoints and observe successful response
// - Access "conditions" endpoint as "actuator" user and observe 403 response
// - Close the current Chrome Incognito browser and open a new one
// - Access "conditions" endpoint as "admin" user and observe successful response
// - Access other endpoints such as "mappings" and observe successful response
@Configuration
public class ActuatorSecurityConfiguration {
}
