package xyz.gabear.quartz.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * CronJob 实体
 */
@Data
@Entity
@Table(name = "cron_job")
@ApiModel
public class CronJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "id", value = "主键,默认是自增", dataType = "long")
    private Long id;

    @Column(name = "job_cn_name")
    @ApiModelProperty(name = "jobCnName", value = "job的中文名字", dataType = "String")
    private String jobCnName;

    @Column(name = "job_name")
    @ApiModelProperty(name = "jobName", value = "job的英文名字", dataType = "String")
    private String jobName;

    @Column(name = "job_class")
    @ApiModelProperty(name = "jobClass", value = "job任务执行的类", dataType = "String")
    private String jobClass;

    @Column(name = "cron")
    @ApiModelProperty(name = "cron", value = "job的调度表达式", dataType = "String")
    private String cron;

    @Column(name = "parameter")
    @ApiModelProperty(name = "parameter", value = "job执行额外参数，json格式", dataType = "String")
    private String parameter;

    @Column(name = "comment")
    @ApiModelProperty(name = "comment", value = "备注信息", dataType = "String")
    private String comment;

    @Column(name = "success_mail")
    @ApiModelProperty(name = "successMail", value = "job执行成功发送邮件人", dataType = "String")
    private String successMail;

    @Column(name = "fail_mail")
    @ApiModelProperty(name = "failMail", value = "job执行失败发送邮件人", dataType = "String")
    private String failMail;

    @Column(name = "job_status")
    @ApiModelProperty(name = "jobStatus", value = "job的状态0-禁用 1-启用", dataType = "long")
    private String jobStatus;
}
