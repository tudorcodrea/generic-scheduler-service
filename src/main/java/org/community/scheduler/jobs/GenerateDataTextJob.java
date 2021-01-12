package org.community.scheduler.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.community.scheduler.entity.TextEntity;
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

		generatedList.add(createTextEntity(invokeParam, new Date()));
		generatedList.add(createTextEntity(invokeParam, Util.getNowDatePlusSecondOffset(10)));
		generatedList.add(createTextEntity(invokeParam, Util.getNowDatePlusSecondOffset(50)));
		generatedList.add(createTextEntity(invokeParam, Util.getNowDatePlusSecondOffset(30)));

		textService.insertList(generatedList);

		log.info("Running job name: " + data.getString("jobName") + " Job textValue param: " + invokeParam
				+ " ---generated: " + generatedList.size());
	}

	private TextEntity createTextEntity(String name, Date date) {
		return TextEntity.builder().textValue(name).textDate(date).build();
	}

}
