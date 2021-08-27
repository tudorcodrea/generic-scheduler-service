package org.community.scheduler.repository;

import java.util.Optional;

import org.community.scheduler.entity.SchedulerJobHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author tudor.codrea
 *
 */
public interface JobHistoryRepository extends JpaRepository<SchedulerJobHistoryEntity, Integer> {

	@Query(value = "SELECT * FROM job_history e WHERE job_name=:jobName ORDER BY last_run DESC LIMIT 1", nativeQuery = true)
	Optional<SchedulerJobHistoryEntity> getLastRunByName(@Param("jobName") String jobName);

}
