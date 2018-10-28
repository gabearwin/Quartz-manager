package xyz.gabear.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.gabear.quartz.common.CronMailUtil;
import xyz.gabear.quartz.domain.CronJob;
import xyz.gabear.quartz.quartz.QuartzService;
import xyz.gabear.quartz.service.CronJobService;

/**
 * 普通的定时任务，继承此类的定时任务，到了执行时间自动执行
 */
@Slf4j
public abstract class AbstractCustomJob implements Job {

    @Autowired
    private CronJobService cronJobService;

    @Autowired
    private CronMailUtil mailUtil;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Long cronId = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzService.DEFAULT_JOB_DATA_KEY);
        CronJob cronJob = cronJobService.findById(cronId);
        try {
            log.info("开始执行:[{}]", cronJob.getJobName());
            doExecute(cronJob.getParameter());
            // mailUtil.sendSimpleMail(cronJob.getSuccessMail(), "定时任务执行成功", cronJob.toString());
        } catch (Exception e) {
            log.error(String.format("执行任务:[%s]失败", cronJob.getJobName()), e);
            // mailUtil.sendSimpleMail(cronJob.getFailMail(), "定时任务执行失败", cronJob.toString() + e.getMessage());
        }
    }

    /**
     * 执行业务逻辑
     *
     * @param jsonParameter 参数
     */
    public abstract void doExecute(String jsonParameter);
}
