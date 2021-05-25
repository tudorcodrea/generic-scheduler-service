package org.community.scheduler.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import liquibase.change.DatabaseChange;
import liquibase.integration.spring.SpringLiquibase;

@Configuration
@ConditionalOnClass(value = {DataSource.class, SpringLiquibase.class, DatabaseChange.class})
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnProperty(value = "genericscheduler.enabled", havingValue = "true", matchIfMissing = false)
@ConditionalOnExpression("${genericscheduler.enabled} and ${spring.liquibase.enabled}")
@AutoConfigureAfter({ DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
	LiquibaseAutoConfiguration.class })
public class GenericSchedulerAutoConfiguration {

	@Bean(name = "genericSchedulerLiquibase")
	@Order(Ordered.LOWEST_PRECEDENCE)
	public SpringLiquibase liquibase(DataSource dataSource) {
	    SpringLiquibase liquibase = new SpringLiquibase();
	    liquibase.setChangeLog("classpath:org/community/scheduler/config/liquibase_generic_scheduler_init.xml");
	    liquibase.setDataSource(dataSource);
	    return liquibase;
	}
	
}
