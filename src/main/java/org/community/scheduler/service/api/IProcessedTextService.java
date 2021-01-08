package org.community.scheduler.service.api;

import java.util.Date;
import java.util.List;

import org.community.scheduler.entity.ProcessedTextEntity;

/**
 * @author tudor.codrea
 *
 */
public interface IProcessedTextService {

	List<ProcessedTextEntity> findAllBetweenTextDates(Date startDate, Date endDate);

	ProcessedTextEntity insert(ProcessedTextEntity sjh);

	List<ProcessedTextEntity> insertList(List<ProcessedTextEntity> tes);
	
}
