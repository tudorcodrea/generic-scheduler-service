package org.community.scheduler.repository.api;

import org.community.scheduler.entity.SchedulerJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tudor.codrea
 *
 */
public interface ISchedulerJobRepository extends JpaRepository<SchedulerJobEntity, Integer> {

}
