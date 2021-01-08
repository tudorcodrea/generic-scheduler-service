package org.community.scheduler.controller;

import java.util.Date;
import java.util.List;

import org.community.scheduler.entity.ProcessedTextEntity;
import org.community.scheduler.service.api.IProcessedTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tudor.codrea
 *
 */
@RestController
@RequestMapping("/text/processed")
public class ProcessedTextController {

	@Autowired
	private IProcessedTextService textService;

	@RequestMapping(value = "/allBetweenTextDates", method = RequestMethod.GET)
	public List<ProcessedTextEntity> findAllBetweenTextDates(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

		return textService.findAllBetweenTextDates(startDate, endDate);
	}

}
