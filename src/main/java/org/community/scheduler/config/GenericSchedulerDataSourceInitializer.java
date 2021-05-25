package org.community.scheduler.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

public class GenericSchedulerDataSourceInitializer {

//extends AbstractDataSourceInitializer {

//	private final GenericSchedulerProperties properties;
//	
//	protected GenericSchedulerDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader) {
//		super(dataSource, resourceLoader);
//		Assert.notNull(properties, "GenericSchedulerProperties must not be null");
//		this.properties = properties;
//	}
//
//	@Override
//	protected DataSourceInitializationMode getMode() {
//		return this.properties.getJdbc().getInitializeSchema();
//	}
//
//	@Override
//	protected String getSchemaLocation() {
//		return this.properties.getJdbc().getSchema();
//	}
//
//	@Override
//	protected String getDatabaseName() {
//		String databaseName = super.getDatabaseName();
//		if ("mysql".equals(databaseName)) {
//			return "mysql_innodb";
//		}
//		if ("postgresql".equals(databaseName)) {
//			return "postgres";
//		}
//		return databaseName;
//	}

}
