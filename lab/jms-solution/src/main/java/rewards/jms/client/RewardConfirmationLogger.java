package rewards.jms.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;

import rewards.RewardConfirmation;

/**
 * A simple logger for reward confirmations.
 */
public class RewardConfirmationLogger {

	private final Logger logger = Logger.getLogger(getClass());

	private List<RewardConfirmation> confirmations = new ArrayList<RewardConfirmation>();

	@JmsListener(destination="rewards.queue.confirmation")
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
