package config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsInfrastructureConfig {

	/**
	 * The connection URL for an in-memory configuration. The queues will not be
	 * made persistent and, since we are using Spring to shut everything down
	 * cleanly at the end, we don't need ActiveMQ to register its shutdown hook
	 * to do cleanup as well (avoiding an unnecessary exception).
	 */
	protected static final String BROKER_URL = "vm://embedded?broker.persistent=false&broker.useShutdownHook=false";

	/**
	 * Create a ConnectionFactory using ActiveMQ:
	 */
	// TODO-01: Configuring an ActiveMQConnectionFactory.
	// We want to use an in-memory configuration suitable for testing.
	// The code is written for you as there is additional configuration needed
	// that is outside the scope of this introduction.
	// If you are happy you understand this code, carry on.
	@Bean
	public ConnectionFactory connectionFactory() {
		// http://activemq.apache.org/objectmessage.html
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);

		// Since ActiveMQ V5.12, oObject messages will only be created from
		// trusted Serializable classes, to avoid a security weakness. For our
		// tests, we just turn this restirction off.
		factory.setTrustAllPackages(true);

		return factory;
	}

	// TODO-02: Create two ActiveMQQueue beans, one for dining and one for
	// confirmations.
	// Use constructor injection to provide a unique name for each queue. You
	// could use any names you like, but we recommend "rewards.queue.dining" and
	// "rewards.queue.confirmation" to match the jms-solution.
	//
	// WARNING: Remember the queue names you select, you will need them later.
	// If you specify the wrong queue name later, the messages are quietly
	// ignored. You DO NOT get an error.
	// Using the wrong queue-names is the most common error in this lab.

	// TODO-08: Create a JMS Listener Container Factory bean to support the
	// @JmsListener annotations.
	// The DefaultJmsListenerContainerFactory is a good class to instantiate for
	// this.
	// Inject its connection factory to the ActiveMQConnectionFactory bean you
	// defined in an earlier step.

	// TODO-09: Mark this class with the annotation needed to enable
	// asynchronous JMS processing.
}
