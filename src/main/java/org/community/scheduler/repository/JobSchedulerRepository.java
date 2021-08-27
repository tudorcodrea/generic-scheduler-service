package org.community.scheduler.repository;

import org.community.scheduler.entity.SchedulerJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tudor.codrea
 *
 */
public interface JobSchedulerRepository extends JpaRepository<SchedulerJobEntity, Integer> {

}
