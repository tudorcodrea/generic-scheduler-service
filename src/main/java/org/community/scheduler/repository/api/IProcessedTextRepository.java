package org.community.scheduler.repository.api;

import java.util.Date;
import java.util.List;

import org.community.scheduler.entity.ProcessedTextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author tudor.codrea
 *
 */
public interface IProcessedTextRepository extends JpaRepository<ProcessedTextEntity, Integer> {

	@Query("select te from TextEntity te where te.textDate between :start and :end")
	List<ProcessedTextEntity> findAllBetweenTextDates(@Param("start")Date startDate, @Param("end")Date endDate); 
	
}
