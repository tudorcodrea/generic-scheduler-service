package org.community.scheduler.controller;

import java.util.List;
import java.util.Optional;

import org.community.scheduler.entity.SchedulerJobHistory;
import org.community.scheduler.service.api.IJobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/scheduler/history")
public class JobHistoryController {

	@Autowired
	private IJobHistoryService jobHistoryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<SchedulerJobHistory> getAll() {
		return jobHistoryService.getAll();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public SchedulerJobHistory getLastRunByJobName(@RequestParam("jobName") String jobName) {

		Optional<SchedulerJobHistory> lastRunByNameOptional = jobHistoryService.getLastRunByName(jobName);

		return lastRunByNameOptional.isPresent() ? lastRunByNameOptional.get() : new SchedulerJobHistory();
	}
	
}
