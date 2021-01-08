package org.community.scheduler.service.api;

import org.community.scheduler.entity.SchedulerJobEntity;
import org.quartz.SchedulerException;

/**
 * @author tudor.codrea
 *
 */
public interface IJobService {

	/**
	 * Create a new task
	 * 
	 */
	String addJob(SchedulerJobEntity schedulerEntity) throws Exception;

	/**
	 * Delete a scheduled job
	 * 
	 * @param jobEntity
	 * @return String - the result of the action
	 * @throws SchedulerException
	 */
	String deleteJob(SchedulerJobEntity jobEntity) throws SchedulerException;

	/**
	 * Resume a scheduled job
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return String - the result of the action
	 * @throws SchedulerException
	 */
	String resumeJob(String jobName, String jobGroup) throws SchedulerException;

	/**
	 * Resume ALL scheduled jobs
	 * 
	 * @throws SchedulerException
	 */
	void resumeAllJobs() throws SchedulerException;

	/**
	 * Pause/Suspend a scheduled job
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return String - the result of the action
	 * @throws SchedulerException
	 */
	String pauseJob(String jobName, String jobGroup) throws SchedulerException;

	/**
	 * Pause/Suspend ALL scheduled jobs
	 * 
	 * @throws SchedulerException
	 */
	void pauseAllJob() throws SchedulerException;

	/**
	 * Obtaining all registered jobs in the Scheduler
	 * 
	 * @return String - all scheduled jobs separated by ; character
	 * @throws SchedulerException
	 */
	String getAllScheduledJobs() throws SchedulerException;

	/**
	 * Get job status, based on the value of {@link TriggerState}
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return String - status value of the job 
	 * @throws SchedulerException
	 */
	String getJobState(String jobName, String jobGroup) throws SchedulerException;

	/**
	 * Change/Modify a registered job
	 * 
	 * @param jobEntity
	 * @return String - the result of the action
	 * @throws Exception
	 */
	String modifyJob(SchedulerJobEntity jobEntity) throws Exception;

}
