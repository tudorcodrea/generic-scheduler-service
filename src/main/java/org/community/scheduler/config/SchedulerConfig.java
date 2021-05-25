package org.community.scheduler.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.community.scheduler.jobs.QuartzJobFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@ComponentScan("org.community.scheduler.config")
public class SchedulerConfig {

	@Autowired
	private QuartzProperties quartzProperties;
	
	@Autowired
	private GenericSchedulerProperties genericSchedulerProperties;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private QuartzJobFactory jobFactory;
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {

		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties());
		
//		it's redundant but needed because of issues from spring quartz library
		properties.put("org.quartz.jobStore.driverDelegateClass", genericSchedulerProperties.getDriverDelegateClass());

		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setDataSource(dataSource);
		factory.setQuartzProperties(properties);
		factory.setJobFactory(jobFactory);

		// in this way, when spring is shut down, it will wait for all started quartz
		// jobs to complete shutdown.
		factory.setWaitForJobsToCompleteOnShutdown(true);

		factory.setOverwriteExistingJobs(false);
		factory.setStartupDelay(1);

		return factory;
	}

	@Bean(name = "scheduler")
	public Scheduler getScheduler() throws SchedulerException, IOException {
		return schedulerFactoryBean().getScheduler();
	}
}
