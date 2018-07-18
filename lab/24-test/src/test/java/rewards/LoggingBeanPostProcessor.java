package rewards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * A simple bean post-processor that logs each newly created bean it sees. Inner
 * beans are ignored.  A very easy way to see what beans you have created and
 * less verbose than turning on Spring's internal logging.
 */
public class LoggingBeanPostProcessor implements BeanPostProcessor {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {

		// Log the names and types of all non inner-beans created
		if (!beanName.contains("inner bean"))
			logger.info("NEW " + bean.getClass().getSimpleName() + " -> "
					+ beanName);

		return bean;
	}

}
