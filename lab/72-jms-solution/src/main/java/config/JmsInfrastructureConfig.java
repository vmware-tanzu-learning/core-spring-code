package config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class JmsInfrastructureConfig {

	/**
	 * The connection URL for an in-memory configuration. The queues will not be
	 * made persistent and, since we are using Spring to shut everything down
	 * cleanly at the end, we don't need ActiveMQ to register its shutdown hook
	 * to do cleanup as well (avoiding an unnecessary exception).
	 */
	public static final String BROKER_URL = "vm://embedded?broker.persistent=false&broker.useShutdownHook=false";

	/**
	 * Create a ConnectionFactory using ActiveMQ:
	 */
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

	/**
	 * Create a Queue for Dining objects using ActiveMQ:
	 */
	@Bean
	public Destination diningQueue() {
		return new ActiveMQQueue("rewards.queue.dining");
	}

	/**
	 * Create a Queue for Confirmation objects using ActiveMQ:
	 */
	@Bean
	public Destination confirmationQueue() {
		return new ActiveMQQueue("rewards.queue.confirmation");
	}

	/**
	 * Create a Factory for creating JMS Listener Containers. Spring will use
	 * this whenever it needs to create an asynchronous JMS Listener Container
	 * to support one of your @JmsListener methods:
	 */
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		return factory;
	}

}
