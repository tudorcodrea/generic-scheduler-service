package org.community.scheduler.service.impl;

import java.util.Date;
import java.util.List;

import org.community.scheduler.entity.ProcessedTextEntity;
import org.community.scheduler.repository.api.IProcessedTextRepository;
import org.community.scheduler.service.api.IProcessedTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessedTextService implements IProcessedTextService {

	@Autowired
	private IProcessedTextRepository textRepository;

	@Override
	public List<ProcessedTextEntity> findAllBetweenTextDates(Date startDate, Date endDate) {
		return textRepository.findAllBetweenTextDates(startDate, endDate);
	}
	
	@Override
	public ProcessedTextEntity insert(ProcessedTextEntity sjh) {
		return textRepository.save(sjh);
	}
	
	@Override
	public List<ProcessedTextEntity> insertList(List<ProcessedTextEntity> tes) {
		return textRepository.saveAll(tes);
	}

}
