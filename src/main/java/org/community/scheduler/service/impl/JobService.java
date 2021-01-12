package org.community.scheduler.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.community.scheduler.entity.SchedulerJobEntity;
import org.community.scheduler.exception.GenericSchedulerException;
import org.community.scheduler.jobs.GenerateDataTextJob;
import org.community.scheduler.jobs.LongBlankJob;
import org.community.scheduler.jobs.TextJob;
import org.community.scheduler.jobs.listener.GenericJobListener;
import org.community.scheduler.jobs.listener.GenericSchedulerListener;
import org.community.scheduler.repository.api.ISchedulerJobRepository;
import org.community.scheduler.service.api.IJobHistoryService;
import org.community.scheduler.service.api.IJobService;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class JobService implements IJobService {

	@Autowired
	@Qualifier("scheduler")
	private Scheduler scheduler;
	
	@Autowired
	private IJobHistoryService jobHistoryService;

	@Autowired
	private ISchedulerJobRepository schedulerRepository;

	/**
	 * Starting all jobs added to DB when the microservice has started
	 */
	@EventListener(classes = ApplicationReadyEvent.class)
	public void startAllAddedJobs() {

		List<SchedulerJobEntity> allSchedulerJobs = schedulerRepository.findAll();

		allSchedulerJobs.forEach(sje -> {
			try {
				this.addJob(sje);
			} catch (Exception e) {
				log.error("Could not restart already registered job: " + sje.toString() + " ERROR: " + e.getMessage());
			}
		});
	}

	/**
	 * Creates a new job based on {@link SchedulerJobEntity}
	 * 
	 * @param schedulerEntity
	 * @return SchedulerJobEntity 
	 * @throws SchedulerException
	 */
	@Override
	public SchedulerJobEntity addJob(SchedulerJobEntity schedulerEntity) throws Exception {

		JobDetail jobDetail = createJobDetail(schedulerEntity);
		Trigger jobTrigger = null;
		
		if (null == jobDetail) {
			throw new GenericSchedulerException("could not create job detail");
		}
		
		if (!schedulerEntity.getCronJob()) {
			jobTrigger = createSimpleJob(schedulerEntity, jobDetail);
		} else {
			// the format of the expression is incorrect
			if (!CronExpression.isValidExpression(schedulerEntity.getCronExpression())) {
				throw new GenericSchedulerException("illegal cron expression"); 
			}
			
			jobTrigger = createCronJob(schedulerEntity, jobDetail);
		}

//		getting first firing DATE
//		Date scheduledStartDate = 
		scheduler.scheduleJob(jobDetail, jobTrigger);

		scheduler.getListenerManager().addJobListener(new GenericJobListener(jobHistoryService));
		scheduler.getListenerManager().addSchedulerListener(new GenericSchedulerListener());

		SchedulerJobEntity addedJob = schedulerRepository.save(schedulerEntity);

		return addedJob;
	}

	/**
	 * Get job status, based on the value of {@link TriggerState}
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return String - status value of the job 
	 * @throws SchedulerException
	 */
	@Override
	public String getJobState(String jobName, String jobGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
		return scheduler.getTriggerState(triggerKey).name();
	}
	
	/**
	 * Obtaining all registered jobs in the Scheduler
	 * 
	 * @return String - all scheduled jobs separated by ; character
	 * @throws SchedulerException
	 */
	@Override
	public List<SchedulerJobEntity> getAllScheduledJobs() throws SchedulerException {
		return schedulerRepository.findAll();
	}

	/**
	 * Pause/Suspend ALL scheduled jobs
	 * 
	 * @throws SchedulerException
	 */
	@Override
	public void pauseAllJobs() throws SchedulerException {
		scheduler.pauseAll();
	}

	/**
	 * Pause/Suspend a scheduled job
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return String - the result of the action
	 * @throws SchedulerException
	 */
	@Override
	public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
		JobKey jobKey = new JobKey(jobName, jobGroup);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail == null) {
			return "fail";
		} else {
			scheduler.pauseJob(jobKey);
			return "success";
		}

	}

	/**
	 * Resume ALL scheduled jobs
	 * 
	 * @throws SchedulerException
	 */
	@Override
	public void resumeAllJobs() throws SchedulerException {
		scheduler.resumeAll();
	}

	/**
	 * Resume a scheduled job
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return String - the result of the action
	 * @throws SchedulerException
	 */
	@Override
	public String resumeJob(String jobName, String jobGroup) throws SchedulerException {

		JobKey jobKey = new JobKey(jobName, jobGroup);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail == null) {
			return "fail";
		} else {
			scheduler.resumeJob(jobKey);
			return "success";
		}
	}

	/**
	 * Delete a scheduled job
	 * 
	 * @param jobEntity
	 * @return SchedulerJobEntity - the result of the action
	 * @throws SchedulerException
	 * @throws GenericSchedulerException 
	 */
	@Override
	public SchedulerJobEntity deleteJob(SchedulerJobEntity jobEntity) throws SchedulerException, GenericSchedulerException {

		JobKey jobKey = new JobKey(jobEntity.getJobName(), jobEntity.getJobGroup());
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail == null) {
			throw new GenericSchedulerException("jobDetail cannot be found");
		} else if (!scheduler.checkExists(jobKey)) {
			throw new GenericSchedulerException("jobKey does not exist");
		} else {
			scheduler.deleteJob(jobKey);
			schedulerRepository.deleteById(jobEntity.getId());

			return jobEntity;
		}
	}

	/**
	 * Change/Modify a registered job
	 * 
	 * @param jobEntity
	 * @return String - the result of the action
	 * @throws Exception
	 */
	@Override
	public SchedulerJobEntity modifyJob(SchedulerJobEntity jobEntity) throws Exception {

		SchedulerJobEntity retVal = new SchedulerJobEntity();

		Optional<SchedulerJobEntity> existingJobEntityOpt = schedulerRepository.findById(jobEntity.getId());
		if (!existingJobEntityOpt.isPresent()) {
			throw new GenericSchedulerException("job not exists");
		}

		SchedulerJobEntity existingJobEntity = existingJobEntityOpt.get();

		String validateVerdict = validateModifiedJob(jobEntity, existingJobEntity);
		if (!StringUtils.isEmpty(validateVerdict)) {
			throw new GenericSchedulerException(validateVerdict);
		}

		if (jobEntity.getCronJob()) {

			boolean modifiedCron = modifyCronScheduledJob(jobEntity);

			if (modifiedCron) {
				retVal = updateSchedulerJobEntity(jobEntity);
			} else {
				throw new GenericSchedulerException("job or trigger not exists");
			}

		} else {

			boolean modifiedSimple = modifySimpleScheduledJob(jobEntity, existingJobEntity);

			if (modifiedSimple) {
				retVal = updateSchedulerJobEntity(jobEntity);
			} else {
				throw new GenericSchedulerException("cannot update job");
			}
		}

		return retVal;
	}

	private JobDetail createJobDetail(SchedulerJobEntity schedulerEntity) throws Exception {

		JobDetail JobDetail = null;
		// Build job information
//      jobGroup = JobType (the job class name declared)
		if ("TextJob".equals(schedulerEntity.getJobGroup())) {

			JobDataMap jobDataMap = createJobDataMap(schedulerEntity.getJobName(), schedulerEntity.getInvokeParam());

			JobDetail = JobBuilder.newJob(TextJob.class)
					.withIdentity(schedulerEntity.getJobName(), schedulerEntity.getJobGroup()).setJobData(jobDataMap)
					.requestRecovery().build();

		} else if ("LongBlankJob".equals(schedulerEntity.getJobGroup())) {
			JobDataMap jobDataMap = createJobDataMap(schedulerEntity.getJobName(), schedulerEntity.getInvokeParam());

			JobDetail = JobBuilder.newJob(LongBlankJob.class)
					.withIdentity(schedulerEntity.getJobName(), schedulerEntity.getJobGroup()).setJobData(jobDataMap)
					.requestRecovery().build();
		} else if ("GenerateDataTextJob".equals(schedulerEntity.getJobGroup())) {
			JobDataMap jobDataMap = createJobDataMap(schedulerEntity.getJobName(), schedulerEntity.getInvokeParam());

			JobDetail = JobBuilder.newJob(GenerateDataTextJob.class)
					.withIdentity(schedulerEntity.getJobName(), schedulerEntity.getJobGroup()).setJobData(jobDataMap)
					.requestRecovery().build();
		} else {
			throw new GenericSchedulerException("Unrecognized job type - job group does not match with existing jobs");
		}
		return JobDetail;
	}
	
	private Trigger createSimpleJob(SchedulerJobEntity schedulerEntity, JobDetail jobDetail) throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = df.parse(schedulerEntity.getStartTime());

		SimpleScheduleBuilder simpleScheduleBuilder = null;

		if (schedulerEntity.getRepeatCount() >= 0) {
			
			simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
					.withRepeatCount(schedulerEntity.getRepeatCount())
					.withIntervalInMilliseconds(schedulerEntity.getRepeatTime());
			
		} else {
			simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().repeatForever()
					.withIntervalInMilliseconds(schedulerEntity.getRepeatTime());
		}

		return TriggerBuilder.newTrigger().withIdentity(schedulerEntity.getJobName(), schedulerEntity.getJobGroup())
				.forJob(jobDetail).usingJobData(jobDetail.getJobDataMap()).startAt(date)
				.withDescription(schedulerEntity.getJobName()).withSchedule(simpleScheduleBuilder).build();
	}

	private CronTrigger createCronJob(SchedulerJobEntity schedulerEntity, JobDetail jobDetail)
			throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = df.parse(schedulerEntity.getStartTime());

		// Expression scheduling Builder (that is, when the task is executed, not
		// immediately)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedulerEntity.getCronExpression())
				.withMisfireHandlingInstructionDoNothing();

		// Construct a new trigger according to the new cronxpression expression
		// expression
		return TriggerBuilder.newTrigger().withIdentity(schedulerEntity.getJobName(), schedulerEntity.getJobGroup())
				.forJob(jobDetail).usingJobData(jobDetail.getJobDataMap()).startAt(date)
				.withDescription(schedulerEntity.getJobName()).withSchedule(scheduleBuilder).build();
	}
	
	/**
	 * @param jobName
	 * @param invokeParam - Transfer parameter(s) from outside to the job
	 * @return
	 */
	private JobDataMap createJobDataMap(String jobName, String invokeParam) {

		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", jobName);

		if (null != invokeParam) {
			jobDataMap.put("invokeParam", invokeParam);
		} else {
			jobDataMap.put("invokeParam", "");
		}

		return jobDataMap;
	}
	
	private SchedulerJobEntity updateSchedulerJobEntity(SchedulerJobEntity jobEntity) {
		SchedulerJobEntity retVal = new SchedulerJobEntity();
		
		Optional<SchedulerJobEntity> optionalSchedulerJobEntity = schedulerRepository.findById(jobEntity.getId());
		if (optionalSchedulerJobEntity.isPresent()) {

			SchedulerJobEntity jobEntityFromDB = optionalSchedulerJobEntity.get();
			jobEntityFromDB.setCronExpression(jobEntity.getCronExpression());
			jobEntityFromDB.setCronJob(jobEntity.getCronJob());
			jobEntityFromDB.setInvokeParam(jobEntity.getInvokeParam());
			jobEntityFromDB.setJobClass(jobEntity.getJobClass());
			jobEntityFromDB.setJobGroup(jobEntity.getJobGroup());
			jobEntityFromDB.setRepeatTime(jobEntity.getRepeatTime());
			jobEntityFromDB.setStartTime(jobEntity.getStartTime());

			retVal = schedulerRepository.save(jobEntityFromDB);
		}
		
		return retVal;
	}

	private boolean modifyCronScheduledJob(SchedulerJobEntity jobEntity) throws Exception {
		
		TriggerKey cronTriggerKey = TriggerKey.triggerKey(jobEntity.getJobName(), jobEntity.getJobGroup());
		JobKey cronJobKey = new JobKey(jobEntity.getJobName(), jobEntity.getJobGroup());
		if (scheduler.checkExists(cronJobKey) && scheduler.checkExists(cronTriggerKey)) {

			if (!CronExpression.isValidExpression(jobEntity.getCronExpression())) {
				throw new GenericSchedulerException("Illegal cron expression");
			}

			CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(cronTriggerKey);
			// Expression schedule builder, does not execute immediately
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCronExpression())
					.withMisfireHandlingInstructionDoNothing();
			// Rebuild the trigger according to the new cronexpression
			cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(cronTriggerKey).withSchedule(scheduleBuilder)
					.build();
			// Modify parameters
			if (!cronTrigger.getJobDataMap().get("invokeParam").equals(jobEntity.getInvokeParam())) {
				cronTrigger.getJobDataMap().put("invokeParam", jobEntity.getInvokeParam());
			}
			// Reset the job execution according to the new trigger
			scheduler.rescheduleJob(cronTriggerKey, cronTrigger);

			return true;

		} else
			return false;
	}

	private boolean modifySimpleScheduledJob(SchedulerJobEntity jobEntity, SchedulerJobEntity currentJobEntity) throws Exception {

		TriggerKey triggerKey = TriggerKey.triggerKey(jobEntity.getJobName(), jobEntity.getJobGroup());
		JobKey jobKey = new JobKey(jobEntity.getJobName(), jobEntity.getJobGroup());
		if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {

			SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
			// Expression schedule builder, does not execute immediately
			SimpleScheduleBuilder scheduleBuilder = null;

			if (jobEntity.getRepeatCount() >= 0 && currentJobEntity.getRepeatCount() >= 0) {

				scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(jobEntity.getRepeatCount())
						.withIntervalInMilliseconds(jobEntity.getRepeatTime());

			} else {
				scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().repeatForever()
						.withIntervalInMilliseconds(jobEntity.getRepeatTime());
			}

			// Rebuild the trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// Modify parameters
			if (!trigger.getJobDataMap().get("invokeParam").equals(jobEntity.getInvokeParam())) {
				trigger.getJobDataMap().put("invokeParam", jobEntity.getInvokeParam());
			}

			// Reset the job execution according to the new trigger
			scheduler.rescheduleJob(triggerKey, trigger);

			return true;
		} else
			return false;
	}

	private String validateModifiedJob(SchedulerJobEntity jobEntity, SchedulerJobEntity currentJobEntity) {
		
		String retVal = "";

		if (!jobEntity.getCronJob().equals(currentJobEntity.getCronJob())) {
			retVal = "cannot change job type";
		}

		if (!currentJobEntity.getCronJob() && jobEntity.getCronJob().equals(currentJobEntity.getCronJob())
				&& ((currentJobEntity.getRepeatCount() < 0 && jobEntity.getRepeatCount() >= 0)
						|| (currentJobEntity.getRepeatCount() >= 0 && jobEntity.getRepeatCount() < 0))) {
			retVal = "cannot change a repeating or a run once job type";
		}
		
		return retVal;
	}
}
