package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Utility class for managing Transactions without Spring (in tests).
 * <p>
 * <b>Note:</b> With Spring's @Transactional tests, you don't need any of this
 * <i>unless</i> you need to run multiple transactions in a single test.
 */
public class TransactionUtils {

	protected final PlatformTransactionManager transactionManager;
	protected final Logger logger;

	private TransactionStatus transactionStatus;

	/**
	 * Create an instance using any available transaction manager.
	 * 
	 * @param transactionManager
	 *            In our tests, either a DataSourceTransactionManager or a
	 *            JpaTransactionManager.
	 */
	public TransactionUtils(PlatformTransactionManager transactionManager) {
		assert (transactionManager != null);

		this.transactionManager = transactionManager;

		logger = LoggerFactory.getLogger(getClass());
		if (logger instanceof ch.qos.logback.classic.Logger)
			((ch.qos.logback.classic.Logger) logger).setLevel(Level.INFO);

	}

	/**
	 * Begin a new transaction, ensuring one is not running already
	 * 
	 * @throws Exception
	 *             A transaction is already running.
	 */
	public void beginTransaction() throws Exception {
		// Make sure no transaction is running
		try {
			transactionStatus = transactionManager
					.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_MANDATORY));
			assert (false); // Force an exception - there should be NO transaction
		} catch (IllegalTransactionStateException e) {
			// Expected behavior, continue
		}

		// Begin a new transaction - just checked that there isn't one
		transactionStatus = transactionManager
				.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));

		assert (transactionStatus != null);
		assert (transactionStatus.isNewTransaction());
		logger.info("NEW " + transactionStatus + " - completed = " + transactionStatus.isCompleted());
	}

	/**
	 * Rollback the current transaction - there must be one.
	 * 
	 * @throws Exception
	 *             A transaction is NOT already running.
	 */
	public void rollbackTransaction() throws Exception {
		// Make sure an exception is running
		try {
			transactionManager
					.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_MANDATORY));
			// Expected behavior, continue
		} catch (IllegalTransactionStateException e) {
			assert (false); // Force an exception - there should be a transaction
		}

		// Rollback the transaction to avoid corrupting other tests
		logger.info("ROLLBACKK " + transactionStatus);
		transactionManager.rollback(transactionStatus);
	}

	/**
	 * Get the current transaction - there should be one.
	 * 
	 * @return
	 */
	public TransactionStatus getCurrentTransaction() {
		TransactionDefinition definition = new DefaultTransactionDefinition(
				DefaultTransactionDefinition.PROPAGATION_MANDATORY);
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		logger.info("TRANSACTION = " + transaction);
		return transaction;
	}

	/**
	 * Get a transaction if there is one or start a new one otherwise.
	 * 
	 * @return The transaction.
	 */
	public TransactionStatus getTransaction() {
		TransactionDefinition definition = new DefaultTransactionDefinition(
				DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		logger.info("TRANSACTION = " + transaction);
		return transaction;
	}

	/**
	 * Start a brand new transaction - forcing a new one if one exists already
	 * (using {@link TransactionDefinition#PROPAGATION_REQUIRES_NEW}).
	 * 
	 * @return
	 */
	public TransactionStatus getNewTransaction() {
		TransactionDefinition definition = new DefaultTransactionDefinition(
				DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		logger.info("TRANSACTION = " + transaction);
		return transaction;
	}

	/**
	 * Is there a transaction running already?
	 * 
	 * @return Yes or no.
	 */
	public boolean transactionExists() {
		try {
			TransactionStatus transaction = getCurrentTransaction();

			if (transaction == null)
				throw new IllegalStateException("No transaction in progress");

			logger.info("TRANSACTION EXISTS - new ? " + transaction.isNewTransaction());
			return true;
		} catch (Exception e) {
			System.out.println(e);
			logger.error("NO TRANSACTION: " + e);
			return false;
		}
	}
}
