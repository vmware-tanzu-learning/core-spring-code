package rewards.jms.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import rewards.RewardConfirmation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple logger for reward confirmations.
 */
public class RewardConfirmationLogger {

	private final Logger logger = LoggerFactory.getLogger(getClass());

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
