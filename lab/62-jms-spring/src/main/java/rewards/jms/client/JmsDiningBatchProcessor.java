package rewards.jms.client;

import java.util.List;

import rewards.Dining;

/**
 * A batch processor that sends dining event notifications via JMS.
 */
public class JmsDiningBatchProcessor implements DiningBatchProcessor {

	// TODO-03: Provide a JmsTemplate field.  
	//	Add a setter or constructor to allow it to be set via dependency injection.

	public void processBatch(List<Dining> batch) {
		//	TODO-04: Loop through each Dining instance, 
		//	sending each one using the JmsTemplate.
	}
}
