package org.community.scheduler.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tudor.codrea
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@Entity
@Table(name = "scheduler_job_history")
public class SchedulerJobHistory implements Serializable {

	private static final long serialVersionUID = 8588692288383675791L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "job_name")
	private String jobName;

	@Column(name = "start_time")
	private String startTime;

	@Column(name = "end_time")
	private String endTime;

	@Column(name = "last_run")
	private Long lastRun;

	@Column(name = "status")
	private String status;

	@Column(name = "exit_status")
	private String exitStatus;

	@Column(name = "details")
	private String details;

}
