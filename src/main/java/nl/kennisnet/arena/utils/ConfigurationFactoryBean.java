package nl.kennisnet.arena.utils;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * FactoryBean which wraps a Commons CompositeConfiguration object for usage
 * with PropertiesLoaderSupport. This allows the configuration object to behave
 * like a normal java.util.Properties object which can be passed on to
 * setProperties() method allowing PropertyOverrideConfigurer and
 * PropertyPlaceholderConfigurer to take advantage of Commons Configuration.
 * <p/>
 * Internally a CompositeConfiguration object is used for merging multiple
 * Configuration objects.
 */
public class ConfigurationFactoryBean implements InitializingBean, FactoryBean<CompositeConfiguration> {
	private static final Logger log = Logger.getLogger(ConfigurationFactoryBean.class);
	private CompositeConfiguration configuration;

	private Configuration[] configurations;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompositeConfiguration getObject() throws Exception {
		return (configuration != null) ? configuration : null;
	}

	@Override
	public Class<? extends CompositeConfiguration> getObjectType() {
		return CompositeConfiguration.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * @return Returns the configurations.
	 */
	public Configuration[] getConfigurations() {
		return configurations;
	}

	/**
	 * Set the configurations objects which will be used as properties.
	 * 
	 * @param configurations
	 *            the configurations to set.
	 */
	public void setConfigurations(Configuration[] configurations) {
		this.configurations = configurations;
	}

	/**
	 * {@inheritDoc}
	 */
	public void afterPropertiesSet() throws Exception {
		if (configurations == null || configurations.length == 0) {
			throw new IllegalArgumentException("at least one configuration");
		}

		configuration = new CompositeConfiguration();

		for (int i = 0; i < configurations.length; i++) {
			log.info("loading: " + configurations[i]);
			configuration.addConfiguration(configurations[i]);
		}
	}

}
