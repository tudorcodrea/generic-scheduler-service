package org.community.scheduler.service.impl;

import java.util.List;
import java.util.Optional;

import org.community.scheduler.entity.SchedulerJobHistory;
import org.community.scheduler.repository.api.IJobHistoryRepository;
import org.community.scheduler.service.api.IJobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The auditing of the scheduled job lifecycle 
 *
 * @author tudor.codrea
 */
@Service
public class JobHistoryService implements IJobHistoryService {

	@Autowired
	private IJobHistoryRepository repository;

	@Override
	public Optional<SchedulerJobHistory> getLastRunByName(String jobName) {
		return repository.getLastRunByName(jobName);
	}
	
	@Override
	public List<SchedulerJobHistory> getAll() {
		return repository.findAll();
	}

	@Override
	public SchedulerJobHistory insert(SchedulerJobHistory sjh) {
		return repository.save(sjh);
	}

	@Override
	public SchedulerJobHistory update(SchedulerJobHistory sjh) {
		return repository.save(sjh);
	}
	

}
