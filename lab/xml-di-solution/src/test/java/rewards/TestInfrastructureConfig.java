package rewards;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
	"rewards/test-infrastructure-config.xml", 
	"config/rewards-config.xml"
})
public class TestInfrastructureConfig {

}
