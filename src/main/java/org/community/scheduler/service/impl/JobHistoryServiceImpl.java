package org.community.scheduler.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.community.scheduler.entity.SchedulerJobHistoryEntity;
import org.community.scheduler.repository.JobHistoryRepository;
import org.community.scheduler.service.api.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The auditing of the scheduled job lifecycle 
 *
 * @author tudor.codrea
 */
@Service
public class JobHistoryServiceImpl implements JobHistoryService {

	@Autowired
	private JobHistoryRepository repository;

	@Override
	public Optional<SchedulerJobHistoryEntity> getLastRunByName(String jobName) {
		return repository.getLastRunByName(jobName);
	}
	
	@Override
	public List<SchedulerJobHistoryEntity> getAll() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public SchedulerJobHistoryEntity insert(SchedulerJobHistoryEntity sjh) {
		return repository.save(sjh);
	}

	@Override
	@Transactional
	public SchedulerJobHistoryEntity update(SchedulerJobHistoryEntity sjh) {
		return repository.save(sjh);
	}

}
