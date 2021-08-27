package org.community.scheduler;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.community.scheduler.config.GenericSchedulerProperties;
import org.community.scheduler.config.SchedulerConfig;
import org.community.scheduler.controller.JobHistoryController;
import org.community.scheduler.controller.JobSchedulerController;
import org.community.scheduler.jobs.QuartzJobFactory;
import org.community.scheduler.repository.JobHistoryRepository;
import org.community.scheduler.repository.JobSchedulerRepository;
import org.community.scheduler.service.impl.JobHistoryServiceImpl;
import org.community.scheduler.service.impl.JobServiceImpl;
import org.community.scheduler.util.JobRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import liquibase.change.DatabaseChange;
import liquibase.integration.spring.SpringLiquibase;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(value = { DataSource.class, SpringLiquibase.class, DatabaseChange.class })
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnProperty(value = "genericscheduler.enabled", havingValue = "true", matchIfMissing = false)
@ConditionalOnExpression("${genericscheduler.enabled} and ${spring.liquibase.enabled}")
@AutoConfigureAfter({ DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		LiquibaseAutoConfiguration.class })
@Import({ JobRegistry.class, QuartzJobFactory.class, SchedulerConfig.class, GenericSchedulerProperties.class,
	JobServiceImpl.class, JobHistoryServiceImpl.class,
		QuartzJobFactory.class, JobSchedulerController.class, JobHistoryController.class })
@EntityScan(basePackages = {"org.community.scheduler.entity"})
public class GenericSchedulerAutoConfiguration {

	@Bean(name = "genericSchedulerLiquibase")
	@Order(Ordered.LOWEST_PRECEDENCE)
	public SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:org/community/scheduler/liquibase_generic_scheduler_init.xml");
		liquibase.setDataSource(dataSource);
		return liquibase;
	}

	@Bean
	public JobSchedulerRepository schedulerJobRepository(EntityManager entityManager) {
		RepositoryFactorySupport factory = new JpaRepositoryFactory(entityManager);
		return factory.getRepository(JobSchedulerRepository.class);
	}
	
	@Bean
	public JobHistoryRepository jobHistoryRepository(EntityManager entityManager) {
		RepositoryFactorySupport factory = new JpaRepositoryFactory(entityManager);
		return factory.getRepository(JobHistoryRepository.class);
	}

}
