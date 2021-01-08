package org.community.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;

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
@Builder
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
}