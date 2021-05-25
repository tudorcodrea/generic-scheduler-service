package org.community.scheduler.entity;

import java.io.Serializable;

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

	public SchedulerJobHistory() {

	}

	public SchedulerJobHistory(Integer id, String jobName, String startTime, String endTime, Long lastRun,
			String status, String exitStatus, String details) {
		super();
		this.id = id;
		this.jobName = jobName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lastRun = lastRun;
		this.status = status;
		this.exitStatus = exitStatus;
		this.details = details;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getLastRun() {
		return lastRun;
	}

	public void setLastRun(Long lastRun) {
		this.lastRun = lastRun;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExitStatus() {
		return exitStatus;
	}

	public void setExitStatus(String exitStatus) {
		this.exitStatus = exitStatus;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((exitStatus == null) ? 0 : exitStatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result + ((lastRun == null) ? 0 : lastRun.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		SchedulerJobHistory other = (SchedulerJobHistory) obj;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (exitStatus == null) {
			if (other.exitStatus != null)
				return false;
		} else if (!exitStatus.equals(other.exitStatus))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (lastRun == null) {
			if (other.lastRun != null)
				return false;
		} else if (!lastRun.equals(other.lastRun))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SchedulerJobHistory [id=" + id + ", jobName=" + jobName + ", startTime=" + startTime + ", endTime="
				+ endTime + ", lastRun=" + lastRun + ", status=" + status + ", exitStatus=" + exitStatus + ", details="
				+ details + "]";
	}

}
