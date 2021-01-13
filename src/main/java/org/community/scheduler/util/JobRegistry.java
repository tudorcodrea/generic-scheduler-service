package org.community.scheduler.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Is holding the Job type beans registered throughout the Spring Context.
 * It is used to find the Job.class declarations at runtime.
 * 
 * @author tudor.codrea
 *
 */
@Component
public class JobRegistry {

	@Autowired
	private List<Job> availableJobs;

	private Map<String, Class<? extends Job>> registry;

	@PostConstruct
	public void initializeRegistry() {
		registry = availableJobs.stream()
				.collect(Collectors.toMap(job -> job.getClass().getSimpleName(), job -> job.getClass()));
	}
	
	public Optional<Class<? extends Job>> findJobByName(String name){
		return Optional.ofNullable(registry.get(name));
	}

}
