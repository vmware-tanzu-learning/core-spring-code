package config;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import rewards.jms.client.DiningBatchProcessor;
import rewards.jms.client.JmsDiningBatchProcessor;
import rewards.jms.client.RewardConfirmationLogger;

@Configuration
public class ClientConfig {

	/**
	 * Given a JmsTemplate create a diningBatchProcessor
	 * to put new messages out on the dining queue:
	 * */
	@Bean
	public DiningBatchProcessor diningBatchProcessor(JmsTemplate jmsTemplate) {
		JmsDiningBatchProcessor processor = new JmsDiningBatchProcessor();
		processor.setJmsTemplate(jmsTemplate);
		return processor;
	}
	
	/**
	 *	Given a ConnectionFactory, create a JmsTemplate 
	 *	which uses the dining queue as its destination: 
	 */
	@Bean
	public JmsTemplate jmsTemplate(
		ConnectionFactory connectionFactory ){
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.setDefaultDestinationName("rewards.queue.dining");
		return template;
	}

	
	/**
	 *	Create an object that knows how to log dining confirmations: 
	 */
	@Bean
	public RewardConfirmationLogger logger() {
		return new RewardConfirmationLogger();
	}
	

	/**
	 *	Create a MessageListener that adapts message traffic
	 *	into calls on the logger's log method: 
	 */
	@Bean
	public MessageListenerAdapter loggerListener () {
		MessageListenerAdapter adapter = 
              new MessageListenerAdapter( logger() );
       adapter.setDefaultListenerMethod( "log" );
       return adapter;
    }	

	/**
	 *	Given a JMS ConnectionFactory, create a listener container
	 *	that listens to the confirmation queue and routes messages
	 *	to the loggerListener: 
	 */
	@Bean
	public AbstractMessageListenerContainer clientListenerContainer (
		ConnectionFactory connectionFactory ) {
		DefaultMessageListenerContainer container = 
			new DefaultMessageListenerContainer();
		container.setConnectionFactory( connectionFactory );
		container.setDestinationName("rewards.queue.confirmation");
		container.setMessageListener( loggerListener() );
		return container;
	}	
}
