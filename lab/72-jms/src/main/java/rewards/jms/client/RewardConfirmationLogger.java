package rewards.jms.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import rewards.RewardConfirmation;

/**
 * A simple logger for reward confirmations.
 */
public class RewardConfirmationLogger {

	private final Logger logger = Logger.getLogger(getClass());

	private List<RewardConfirmation> confirmations = new ArrayList<RewardConfirmation>();

	//	TODO-07: Use an annotation to designate this log method as a JMS listener.
	//	Set the destination to the confirmation queue name defined earlier.
	//	Note that unlike the last step, this method returns void, so no response destination is needed.
	
	public void log(RewardConfirmation rewardConfirmation) {
		this.confirmations.add(rewardConfirmation);
		if (logger.isInfoEnabled()) {
			logger.info("received confirmation: " + rewardConfirmation);
		}
	}

	public List<RewardConfirmation> getConfirmations() {
		return Collections.unmodifiableList(confirmations);
	}
}
