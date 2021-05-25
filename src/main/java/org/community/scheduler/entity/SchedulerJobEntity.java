package org.community.scheduler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tudor.codrea
 *
 */
@Entity
@Table(name = "scheduler_job")
public class SchedulerJobEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "job_name")
	private String jobName;

	@Column(name = "job_group")
	private String jobGroup;

	@Column(name = "job_class")
	private String jobClass;

	@Column(name = "cron_expression")
	private String cronExpression;

	@Column(name = "start_time")
	private String startTime;

	@Column(name = "repeat_time")
	private Long repeatTime;

	@Column(name = "repeat_count", columnDefinition = "integer default 0")
	private Integer repeatCount;

	@Column(name = "cron_job", columnDefinition = "boolean default true")
	private Boolean cronJob;

	@Column(name = "invoke_param")
	private String invokeParam;

	public SchedulerJobEntity() {

	}

	public SchedulerJobEntity(Integer id, String jobName, String jobGroup, String jobClass, String cronExpression,
			String startTime, Long repeatTime, Integer repeatCount, Boolean cronJob, String invokeParam) {
		super();
		this.id = id;
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.jobClass = jobClass;
		this.cronExpression = cronExpression;
		this.startTime = startTime;
		this.repeatTime = repeatTime;
		this.repeatCount = repeatCount;
		this.cronJob = cronJob;
		this.invokeParam = invokeParam;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Long getRepeatTime() {
		return repeatTime;
	}

	public void setRepeatTime(Long repeatTime) {
		this.repeatTime = repeatTime;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Boolean getCronJob() {
		return cronJob;
	}

	public void setCronJob(Boolean cronJob) {
		this.cronJob = cronJob;
	}

	public String getInvokeParam() {
		return invokeParam;
	}

	public void setInvokeParam(String invokeParam) {
		this.invokeParam = invokeParam;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cronExpression == null) ? 0 : cronExpression.hashCode());
		result = prime * result + ((cronJob == null) ? 0 : cronJob.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invokeParam == null) ? 0 : invokeParam.hashCode());
		result = prime * result + ((jobClass == null) ? 0 : jobClass.hashCode());
		result = prime * result + ((jobGroup == null) ? 0 : jobGroup.hashCode());
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result + ((repeatCount == null) ? 0 : repeatCount.hashCode());
		result = prime * result + ((repeatTime == null) ? 0 : repeatTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedulerJobEntity other = (SchedulerJobEntity) obj;
		if (cronExpression == null) {
			if (other.cronExpression != null)
				return false;
		} else if (!cronExpression.equals(other.cronExpression))
			return false;
		if (cronJob == null) {
			if (other.cronJob != null)
				return false;
		} else if (!cronJob.equals(other.cronJob))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invokeParam == null) {
			if (other.invokeParam != null)
				return false;
		} else if (!invokeParam.equals(other.invokeParam))
			return false;
		if (jobClass == null) {
			if (other.jobClass != null)
				return false;
		} else if (!jobClass.equals(other.jobClass))
			return false;
		if (jobGroup == null) {
			if (other.jobGroup != null)
				return false;
		} else if (!jobGroup.equals(other.jobGroup))
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (repeatCount == null) {
			if (other.repeatCount != null)
				return false;
		} else if (!repeatCount.equals(other.repeatCount))
			return false;
		if (repeatTime == null) {
			if (other.repeatTime != null)
				return false;
		} else if (!repeatTime.equals(other.repeatTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SchedulerJobEntity [id=" + id + ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", jobClass="
				+ jobClass + ", cronExpression=" + cronExpression + ", startTime=" + startTime + ", repeatTime="
				+ repeatTime + ", repeatCount=" + repeatCount + ", cronJob=" + cronJob + ", invokeParam=" + invokeParam
				+ "]";
	}

}