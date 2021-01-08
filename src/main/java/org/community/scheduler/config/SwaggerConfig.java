package org.community.scheduler.config;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tudor.codrea
 *
 */
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("org.community.scheduler.controller")).paths(PathSelectors.any())
				.build().apiInfo(new ApiInfoBuilder().version("1.0").title("Job Scheduler API")
						.description("Documentation Job Scheduler API v1.0").build());
	}
}
