package org.community.scheduler.controller;

import java.util.List;
import java.util.Optional;

import org.community.scheduler.entity.SchedulerJobHistoryEntity;
import org.community.scheduler.service.api.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auditing the registered scheduled jobs
 * 
 * @author tudor.codrea
 *
 */
@RestController
@RequestMapping("/scheduler/history")
public class JobHistoryController {

	@Autowired
	private JobHistoryService jobHistoryService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<SchedulerJobHistoryEntity> getAll() {
		return jobHistoryService.getAll();
	}

	@RequestMapping(value = "/job", method = RequestMethod.GET)
	public SchedulerJobHistoryEntity getLastRunByJobName(@RequestParam("jobName") String jobName) {

		Optional<SchedulerJobHistoryEntity> lastRunByNameOptional = jobHistoryService.getLastRunByName(jobName);

		return lastRunByNameOptional.isPresent() ? lastRunByNameOptional.get() : new SchedulerJobHistoryEntity();
	}
	
}
