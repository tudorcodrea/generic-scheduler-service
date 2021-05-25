package org.community.scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for the Generic Scheduler.
 */
@Configuration
@ConfigurationProperties("genericscheduler")
public class GenericSchedulerProperties {

	private boolean enabled = false;

	private String driverDelegateClass;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDriverDelegateClass() {
		return driverDelegateClass;
	}

	public void setDriverDelegateClass(String driverDelegateClass) {
		this.driverDelegateClass = driverDelegateClass;
	}

}
