package org.community.scheduler.jobs.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author tudor.codrea
 *
 */
@Component
public class GenericSchedulerListener implements SchedulerListener {

	private Logger log = LoggerFactory.getLogger(GenericSchedulerListener.class);
	
	@Override
	public void jobScheduled(Trigger trigger) {
		String jobName = trigger.getJobDataMap().getString("jobName");
		log.info("Scheduled job: " + jobName + ", start time: " + trigger.getStartTime() + ", end time: "
				+ trigger.getEndTime());
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		log.info("Unscheduled job: " + triggerKey.getName() + " in group: " + triggerKey.getGroup());
	}

	@Override
	public void triggerFinalized(Trigger trigger) {

		String jobName = trigger.getJobDataMap().getString("jobName");
		log.info("Trigger ended job: " + jobName + ", end time: " + trigger.getEndTime());
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		log.info("Paused job: " + triggerKey.getName() + " in group: " + triggerKey.getGroup());
	}

	@Override
	public void triggersPaused(String triggerGroup) {
		log.info("Paused jobs in group: " + triggerGroup);

	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		log.info("Resumed job: " + triggerKey.getName() + " in group: " + triggerKey.getGroup());
	}

	@Override
	public void triggersResumed(String triggerGroup) {
		log.info("Resumed jobs in group: " + triggerGroup);
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		String jobName = jobDetail.getJobDataMap().getString("jobName");
		String jobInvokeParam = jobDetail.getJobDataMap().getString("invokeParam");
		log.info("Added job: " + jobName + ", with invoke param: " + jobInvokeParam);
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		log.info("Deleted job: " + jobKey.getName() + " in group: " + jobKey.getGroup());
	}

	@Override
	public void jobPaused(JobKey jobKey) {
		log.info("Paused job: " + jobKey.getName() + " in group: " + jobKey.getGroup());
	}

	@Override
	public void jobsPaused(String jobGroup) {
		log.info("Paused jobs in group: " + jobGroup);
	}

	@Override
	public void jobResumed(JobKey jobKey) {
		log.info("Resumed job: " + jobKey.getName() + " in group: " + jobKey.getGroup());
	}

	@Override
	public void jobsResumed(String jobGroup) {
		log.info("Resumed jobs in group: " + jobGroup);
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		log.info("Scheduler error: " + msg + " cause: " + cause.getMessage());
	}

	@Override
	public void schedulerInStandbyMode() {
		log.info("Scheduler in Standby Mode");
	}

	@Override
	public void schedulerStarted() {
		log.info("Scheduler Started");
	}

	@Override
	public void schedulerStarting() {
		log.info("Scheduler starting");
	}

	@Override
	public void schedulerShutdown() {
		log.info("Scheduler Shutdown");
	}

	@Override
	public void schedulerShuttingdown() {
		log.info("Scheduler shuttingdown");
	}

	@Override
	public void schedulingDataCleared() {
		log.info("Scheduler data cleared");
	}

}
