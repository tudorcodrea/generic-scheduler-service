package org.community.scheduler.service.api;

import java.util.List;
import java.util.Optional;

import org.community.scheduler.entity.SchedulerJobHistoryEntity;

/**
 * The auditing of the scheduled job lifecycle 
 *
 * @author tudor.codrea
 */
public interface JobHistoryService {

	List<SchedulerJobHistoryEntity> getAll();
	
	Optional<SchedulerJobHistoryEntity> getLastRunByName(String jobName);

	SchedulerJobHistoryEntity insert(SchedulerJobHistoryEntity sjh);
	SchedulerJobHistoryEntity update(SchedulerJobHistoryEntity sjh);
}
