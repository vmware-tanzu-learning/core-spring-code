package rewards.internal.exception;


@SuppressWarnings("serial")
public class RewardDataAccessException extends RuntimeException{

	public RewardDataAccessException() {
		super();
	}

	public RewardDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public RewardDataAccessException(String message) {
		super(message);
	}

	public RewardDataAccessException(Throwable cause) {
		super(cause);
	}

}
