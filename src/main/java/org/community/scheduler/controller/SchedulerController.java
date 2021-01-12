package org.community.scheduler.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.time.StopWatch;
import org.community.scheduler.entity.SchedulerJobEntity;
import org.community.scheduler.service.api.IJobService;
import org.community.scheduler.util.rest.response.Response;
import org.community.scheduler.util.rest.response.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Managing the scheduler and the jobs
 * 
 * @author tudor.codrea
 *
 */
@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

	@Autowired
	private IJobService jobService;

	// Add a job
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Response<SchedulerJobEntity>> addJob(@RequestBody SchedulerJobEntity jobEntity)
			throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();

		SchedulerJobEntity addedJob = jobService.addJob(jobEntity);
		List<SchedulerJobEntity> allJobs = Arrays.asList(addedJob);

		watch.stop();
		return new ResponseEntity<>(new Response<SchedulerJobEntity>(ResponseStatus.SUCCESS, watch.getTime(), allJobs,
				allJobs.size(), 0, 0, allJobs.size(), ""), HttpStatus.OK);
	}

	// Pause job
	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	public ResponseEntity<Response<String>> pauseJob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		
		String pauseJobResult = jobService.pauseJob(jobEntity.getJobName(), jobEntity.getJobGroup());
		List<String> results = Arrays.asList(pauseJobResult);
		
		watch.stop();
		return new ResponseEntity<>(new Response<String>(ResponseStatus.SUCCESS, watch.getTime(), results,
				results.size(), 0, 0, results.size(), ""), HttpStatus.OK);
	}

	// Restore job
	@RequestMapping(value = "/resume", method = RequestMethod.POST)
	public ResponseEntity<Response<String>> resumeJob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		
		String resumeJobResult = jobService.resumeJob(jobEntity.getJobName(), jobEntity.getJobGroup());
		List<String> results = Arrays.asList(resumeJobResult);
		
		watch.stop();
		return new ResponseEntity<>(new Response<String>(ResponseStatus.SUCCESS, watch.getTime(), results,
				results.size(), 0, 0, results.size(), ""), HttpStatus.OK);
	}

	// Delete job
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<Response<SchedulerJobEntity>> deleteJob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		
		SchedulerJobEntity deleteJobResult = jobService.deleteJob(jobEntity);
		List<SchedulerJobEntity> results = Arrays.asList(deleteJobResult);

		watch.stop();
		return new ResponseEntity<>(new Response<SchedulerJobEntity>(ResponseStatus.SUCCESS, watch.getTime(), results,
				results.size(), 0, 0, results.size(), ""), HttpStatus.OK);
	}

	// Modify
	@RequestMapping(value = "/", method = RequestMethod.PATCH)
	public ResponseEntity<Response<SchedulerJobEntity>> modifyJob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		
		SchedulerJobEntity modifyJobResult = jobService.modifyJob(jobEntity);
		List<SchedulerJobEntity> results = Arrays.asList(modifyJobResult);
		
		watch.stop();
		return new ResponseEntity<>(new Response<SchedulerJobEntity>(ResponseStatus.SUCCESS, watch.getTime(), results,
				results.size(), 0, 0, results.size(), ""), HttpStatus.OK);
	}

	// Suspend all
	@RequestMapping(value = "/pause-all", method = RequestMethod.GET)
	public ResponseEntity<Response<String>> pauseAllJobs() throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		
		jobService.pauseAllJobs();
		List<String> results = Arrays.asList("Pause all Success");
		
		watch.stop();
		return new ResponseEntity<>(new Response<String>(ResponseStatus.SUCCESS, watch.getTime(), results,
				results.size(), 0, 0, results.size(), ""), HttpStatus.OK);
	}

	// Restore all
	@RequestMapping(value = "/resume-all", method = RequestMethod.GET)
	public ResponseEntity<Response<String>> resumeAllJobs() throws Exception {
		
		StopWatch watch = new StopWatch();
		watch.start();
		
		jobService.resumeAllJobs();
		List<String> results = Arrays.asList("Pause all Success");
		
		watch.stop();
		return new ResponseEntity<>(new Response<String>(ResponseStatus.SUCCESS, watch.getTime(), results,
				results.size(), 0, 0, results.size(), ""), HttpStatus.OK);
	}

	// Get getJobState
	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public ResponseEntity<Response<String>> getJobState(@RequestParam("name") @NotBlank String jobName,
			@RequestParam("group") @NotBlank String jobGroup) throws Exception {
		
		StopWatch watch = new StopWatch();
		watch.start();
		
		String result = jobService.getJobState(jobName, jobGroup);
		List<String> results = Arrays.asList(result);
		
		watch.stop();
		return new ResponseEntity<>(new Response<String>(ResponseStatus.SUCCESS, watch.getTime(), results,
				results.size(), 0, 0, results.size(), ""), HttpStatus.OK);
	}

	// Get all registered jobs
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Response<SchedulerJobEntity>> getAllScheduledJobs() throws Exception {
		
		StopWatch watch = new StopWatch();
		watch.start();
		
		List<SchedulerJobEntity> results = jobService.getAllScheduledJobs();
		
		watch.stop();
		return new ResponseEntity<>(new Response<SchedulerJobEntity>(ResponseStatus.SUCCESS, watch.getTime(), results,
				results.size(), 0, 0, results.size(), ""), HttpStatus.OK);
	}
}
