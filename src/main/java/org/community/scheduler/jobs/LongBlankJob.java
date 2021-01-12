package org.community.scheduler.jobs;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.community.scheduler.entity.ProcessedTextEntity;
import org.community.scheduler.entity.TextEntity;
import org.community.scheduler.service.api.IProcessedTextService;
import org.community.scheduler.service.api.ITextService;
import org.community.scheduler.util.Util;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * Demo job simulating a long time execution job Concurrent Job
 * 
 * @author tudor.codrea
 *
 */
//@DisallowConcurrentExecution
@Component
@Log4j2
public class LongBlankJob implements Job {

	@Autowired
	private ITextService textService;

	@Autowired
	private IProcessedTextService processedTextService;

	private final ScheduledExecutorService executorScheduler = Executors.newScheduledThreadPool(1);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = context.getTrigger().getJobDataMap();

		String invokeParam = data.getString("invokeParam");
		log.info("Executing job name: " + data.getString("jobName") + " param: " + invokeParam);

		int jobOffset = StringUtils.isNumeric(invokeParam) ? Integer.parseInt(invokeParam) : 1;

		List<TextEntity> textEntitiesBetweenTextDatesDates = textService.findAllBetweenTextDates(Util.getNowDateMinuteOffset(jobOffset),
				Util.getNowDateMinuteOffset(0));

		Runnable delayedTask = () -> {
			textEntitiesBetweenTextDatesDates.forEach(i -> {
				processedTextService.insert(fromTextEntity((TextEntity) i));
			});
		};
		
//		delay by the invokeParam
		executorScheduler.schedule(delayedTask, jobOffset, TimeUnit.MINUTES);

	}

	private ProcessedTextEntity fromTextEntity(TextEntity te) {
		return ProcessedTextEntity.builder().textDate(te.getTextDate()).textValue(te.getTextValue()).build();
	}

}
