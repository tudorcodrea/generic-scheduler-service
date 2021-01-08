package org.community.scheduler.controller;

import javax.validation.constraints.NotBlank;

import org.community.scheduler.entity.SchedulerJobEntity;
import org.community.scheduler.service.api.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
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
	public ResponseEntity<String> addjob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {

		String addJob = jobService.addJob(jobEntity);

		return new ResponseEntity<String>(addJob, HttpStatus.OK);
	}

	// Pause job
	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	public ResponseEntity<String> pausejob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {

		String pauseJobResult = jobService.pauseJob(jobEntity.getJobName(), jobEntity.getJobGroup());

		return new ResponseEntity<String>(pauseJobResult, HttpStatus.OK);
	}

	// Restore job
	@RequestMapping(value = "/resume", method = RequestMethod.POST)
	public ResponseEntity<String> resumejob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {

		String resumeJobResult = jobService.resumeJob(jobEntity.getJobName(), jobEntity.getJobGroup());

		return new ResponseEntity<String>(resumeJobResult, HttpStatus.OK);
	}

	// Delete job
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletjob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {

		String deleteJobResult = jobService.deleteJob(jobEntity);

		return new ResponseEntity<String>(deleteJobResult, HttpStatus.OK);
	}

	// Modify
	@RequestMapping(value = "/", method = RequestMethod.PATCH)
	public ResponseEntity<String> modifyJob(@RequestBody SchedulerJobEntity jobEntity) throws Exception {
		
		String modifyJobResult = jobService.modifyJob(jobEntity);
		return new ResponseEntity<String>(modifyJobResult, HttpStatus.OK);
	}

	// Suspend all
	@RequestMapping(value = "/pause-all", method = RequestMethod.GET)
	public ResponseEntity<String> pauseAllJob() throws Exception {

		jobService.pauseAllJob();
		return new ResponseEntity<String>("Pause all Success", HttpStatus.OK);
	}

	// Restore all
	@RequestMapping(value = "/resume-all", method = RequestMethod.GET)
	public ResponseEntity<String> resumeAllJob() throws Exception {
		jobService.resumeAllJobs();
		return new ResponseEntity<String>("Pause all Success", HttpStatus.OK);
	}

	// Get getJobState
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> getJobState(@RequestParam("name") @NotBlank String jobName,
			@RequestParam("group") @NotBlank String jobGroup) throws Exception {
		String result = jobService.getJobState(jobName, jobGroup);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	// Get all registered jobs
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> getAllScheduledJobs() throws Exception {
		String result = jobService.getAllScheduledJobs();
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
