package org.community.scheduler.jobs;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * Registering Quartz context to Spring Boot context as beans
 * 
 * @author tudor.codrea
 *
 */
@Component
public class QuartzJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;

    public void setApplicationContext(final ApplicationContext context) {
    	capableBeanFactory = context.getAutowireCapableBeanFactory();
    }
	
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		// Call the method of the parent class
		Object jobInstance = super.createJobInstance(bundle);
		// Carry out injection
		capableBeanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
}
