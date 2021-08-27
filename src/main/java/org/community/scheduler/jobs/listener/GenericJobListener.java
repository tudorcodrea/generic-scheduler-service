package org.community.scheduler.jobs.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.community.scheduler.entity.SchedulerJobHistoryEntity;
import org.community.scheduler.service.api.JobHistoryService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tudor.codrea
 *
 */
public class GenericJobListener implements JobListener {

	private Logger log = LoggerFactory.getLogger(GenericJobListener.class);
	
	private JobHistoryService jobHistoryService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS");
	
	public GenericJobListener(JobHistoryService jobHistoryService) {
		this.jobHistoryService = jobHistoryService;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {

		String jobName = context.getJobDetail().getJobDataMap().getString("jobName");
		String jobInvokeParam = context.getJobDetail().getJobDataMap().getString("invokeParam");
		log.info("The job is going to be executed: " + jobName + ", jobInvokeParam: " + jobInvokeParam);

		createJobHistory(context);
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {

		String jobName = context.getJobDetail().getJobDataMap().getString("jobName");
		log.warn("The job was veoted and not executed: " + jobName);
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

		String jobName = context.getJobDetail().getJobDataMap().getString("jobName");
		log.info("The job was executed: " + jobName + ", any exception: "
				+ getJobExecutionExceptionDetails(jobException));

		StringBuilder sb = new StringBuilder();
		SchedulerJobHistoryEntity jobHistory = null;

		if (context.getMergedJobDataMap().containsKey("jobHistory")) {
			jobHistory = (SchedulerJobHistoryEntity) context.getMergedJobDataMap().get("jobHistory");
		}

		if (jobHistory != null) {

			sb.append(jobHistory.getDetails());
			sb.append(", invokeParam: ");
			sb.append(context.getJobDetail().getJobDataMap().getString("invokeParam"));

			if (null != jobException) {

				sb.append(", executionException: ");
				sb.append(getJobExecutionExceptionDetails(jobException));
				jobHistory.setDetails(sb.toString());
				
				jobHistory.setStatus("Failed");
				jobHistory.setExitStatus("ERROR");
				
			} else {
				jobHistory.setDetails(sb.toString());
				jobHistory.setStatus("Completed");
				jobHistory.setExitStatus("SUCCESS");
			}

			jobHistory.setEndTime(sdf.format(new Date()));
			jobHistory.setLastRun(context.getFireTime().getTime());

			try {
				this.jobHistoryService.update(jobHistory);
			} catch (Exception e) {
				log.error("Failed to update job history:" + e);
			}
		}
	}

	private String getJobExecutionExceptionDetails(JobExecutionException jobException) {
		if (null != jobException) {
			return jobException.getMessage();
		} else {
			return " NO";
		}
	}

	private void createJobHistory(JobExecutionContext executionContext) {
		SchedulerJobHistoryEntity jh = new SchedulerJobHistoryEntity();
		jh.setJobName(executionContext.getJobDetail().getJobDataMap().getString("jobName"));
		jh.setDetails(executionContext.getFireInstanceId());
		jh.setStartTime(sdf.format(executionContext.getFireTime()));
		jh.setStatus("Running");
		try {
			SchedulerJobHistoryEntity newJobHistory = this.jobHistoryService.insert(jh);
			executionContext.getMergedJobDataMap().put("jobHistory", newJobHistory);
		} catch (Exception e) {
			log.error("Job history insert issue: ", e);
		}
	}
}
