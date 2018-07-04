package rewards;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * This class sets up Simple JNDI, creates an embedded dataSource and registers
 * it with JNDI. Normally JNDI is provided by a container such as Tomcat. This
 * allows JNDI to be used standalone in a test.
 * <p>
 * We need this to be registered and available before any Spring beans are
 * created, or our JNDI lookup will fail. We have therefore made this bean into
 * a <tt>BeanFactoryPostProcessor</tt> - it will be invoked just before the
 * first bean is created.
 */
public class SimpleJndiHelper implements BeanFactoryPostProcessor {

	public static final String REWARDS_DB_JNDI_PATH = "java:/comp/env/jdbc/rewards";
	protected Logger logger = Logger.getLogger(SimpleJndiHelper.class);

	public void doJndiSetup() {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.osjava.sj.SimpleContextFactory");
		System.setProperty("org.osjava.sj.root", "target/test-classes/jndi");
		System.setProperty("org.osjava.jndi.delimiter", "/");
		System.setProperty("org.osjava.sj.jndi.shared", "true");

		logger.info("Running JDNI setup");

		try {
			InitialContext ic = new InitialContext();

			// Construct DataSource
			DataSource ds = new EmbeddedDatabaseBuilder()
					.addScript("classpath:rewards/testdb/schema.sql")
					.addScript("classpath:rewards/testdb/data.sql")
					.build();

			// Bind as a JNDI resource
			ic.rebind(REWARDS_DB_JNDI_PATH, ds);
			logger.info("JNDI Resource '" + REWARDS_DB_JNDI_PATH
					+ "' instanceof " + ds.getClass().getSimpleName());
		} catch (NamingException ex) {
			Logger.getLogger(getClass()).error(ex);
			ex.printStackTrace();
			System.exit(0);
		}

		logger.info("JNDI Registrations completed.");
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		doJndiSetup();
		return;
	}

}
