package org.community.scheduler.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.community.scheduler.entity.ProcessedTextEntity;
import org.community.scheduler.entity.TextEntity;
import org.community.scheduler.service.api.IProcessedTextService;
import org.community.scheduler.service.api.ITextService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * Demo job simulating a long time execution job
 * 
 * @author tudor.codrea
 *
 */
//@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
@Component
@Log4j2
public class LongBlankJob implements Job {

	@Autowired
	private ITextService textService;
	
	@Autowired
	private IProcessedTextService processedTextService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = context.getTrigger().getJobDataMap();

		log.info("Executing job name: " + data.getString("jobName") + " param: " + data.getString("invokeParam"));

		int jobOffset = StringUtils.isNumeric(data.getString("invokeParam")) ? Integer.parseInt(data.getString("invokeParam")) : -1;
		
		List<TextEntity> textEntitiesBetweenTextDatesDates = textService.findAllBetweenTextDates(getFrom(jobOffset), getTo());
		
		
		try {
			Thread.sleep(1000 * 60 + jobOffset);
		} catch (InterruptedException e) {
			log.error("Job execution error: " + e);
		}
		
		textEntitiesBetweenTextDatesDates.forEach(i -> {
			processedTextService.insert(fromTextEntity((TextEntity) i));
		});
		
	}
	
	private Date getFrom(int minuteOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MINUTE, -1 * minuteOffset);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (log.isDebugEnabled()) {
			log.debug("From date:" + cal.toString());
		}
		return cal.getTime();
	}

	private Date getTo() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (log.isDebugEnabled()) {
			log.debug("To date:" + cal.toString());
		}
		return cal.getTime();
	}
	
	private ProcessedTextEntity fromTextEntity(TextEntity te) {
		return ProcessedTextEntity.builder().textDate(te.getTextDate()).textValue(te.getTextValue()).build();
	}

}
