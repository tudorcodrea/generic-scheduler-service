package org.community.scheduler.service.api;

import java.util.List;
import java.util.Optional;

import org.community.scheduler.entity.SchedulerJobHistory;

/**
 * The auditing of the scheduled job lifecycle 
 *
 * @author tudor.codrea
 */
public interface IJobHistoryService {

	List<SchedulerJobHistory> getAll();
	
	Optional<SchedulerJobHistory> getLastRunByName(String jobName);

	SchedulerJobHistory insert(SchedulerJobHistory sjh);
	SchedulerJobHistory update(SchedulerJobHistory sjh);
}
