package nl.kennisnet.arena.utils;

import java.io.File;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.reloading.ReloadingStrategy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * Factorybean to create a configuration of properties when the resource exists.
 */
public class PropertyConfigurationFactoryBean implements InitializingBean, FactoryBean<Configuration> {

	private static final Logger log = Logger.getLogger(PropertyConfigurationFactoryBean.class);

	private Configuration configuration;

	private Resource location;

	private boolean reload;

	public void setLocation(Resource location) {
		this.location = location;
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public void afterPropertiesSet() throws Exception {
		if (location.exists()) {
			log.info(String.format("resource %s does exists.", location.getDescription()));
			// If it is a file, use reloading strategy.
			PropertiesConfiguration propconfig = new PropertiesConfiguration(location.getURL());
			if (reload) {
				File f = ConfigurationUtils.fileFromURL(location.getURL());
				if (f != null && f.exists()) {
					log.info(String.format("resource %s will be configured with file reloading strategy.", location.getDescription()));
					ReloadingStrategy strategy = new FileChangedReloadingStrategy();
					propconfig.setReloadingStrategy(strategy);
				}
			}
			configuration = propconfig;
		} else {
			log.info(String.format("resource %s does not exist, using base config.", location.getDescription()));
			this.configuration = new BaseConfiguration();
		}
	}

	public boolean isSingleton() {
		return true;
	}

	public Resource getLocation() {
		return location;
	}

	public boolean isReload() {
		return reload;
	}

	@Override
	public Configuration getObject() throws Exception {
		return configuration;
	}

	@Override
	public Class<? extends Configuration> getObjectType() {
		return Configuration.class;
	}
}
