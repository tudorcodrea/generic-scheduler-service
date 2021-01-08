package org.community.scheduler.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.community.scheduler.entity.TextEntity;
import org.community.scheduler.service.api.ITextService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * Demo Job used to generate text entities data
 * 
 * @author tudor.codrea
 */
@Component
@Log4j2
public class GenerateDataTextJob implements Job {

	@Autowired
	private ITextService textService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = context.getTrigger().getJobDataMap();
		String invokeParam = (String) data.get("invokeParam");

		List<TextEntity> generatedList = new ArrayList<TextEntity>();

		generatedList.add(createTextEntity(invokeParam, getNow()));
		generatedList.add(createTextEntity(invokeParam, getNowSecond1()));
		generatedList.add(createTextEntity(invokeParam, getNowSecond5()));
		generatedList.add(createTextEntity(invokeParam, getNowSecond3()));

		textService.insertList(generatedList);

		log.info("Running job name: " + data.getString("jobName") + " Job textValue param: " + invokeParam
				+ " ---generated: " + generatedList.size());
	}

	private TextEntity createTextEntity(String name, Date date) {
		return TextEntity.builder().textValue(name).textDate(date).build();
	}

	private Date getNow() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MILLISECOND, 0);
		if (log.isDebugEnabled()) {
			log.debug("From date:" + cal.toString());
		}
		return cal.getTime();
	}

	private Date getNowSecond1() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.SECOND, 10);
		cal.set(Calendar.MILLISECOND, 0);
		if (log.isDebugEnabled()) {
			log.debug("From date:" + cal.toString());
		}
		return cal.getTime();
	}

	private Date getNowSecond5() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.SECOND, 50);
		cal.set(Calendar.MILLISECOND, 0);
		if (log.isDebugEnabled()) {
			log.debug("From date:" + cal.toString());
		}
		return cal.getTime();
	}

	private Date getNowSecond3() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.SECOND, 30);
		cal.set(Calendar.MILLISECOND, 0);
		if (log.isDebugEnabled()) {
			log.debug("From date:" + cal.toString());
		}
		return cal.getTime();
	}

}
