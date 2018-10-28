package xyz.gabear.quartz.quartz.impl;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gabear.quartz.domain.CronJob;
import xyz.gabear.quartz.enums.JobResultEnum;
import xyz.gabear.quartz.enums.JobStatusEnum;
import xyz.gabear.quartz.exception.CronJobException;
import xyz.gabear.quartz.quartz.QuartzClassLoader;
import xyz.gabear.quartz.quartz.QuartzService;

import java.util.Optional;

@Slf4j
@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 默认的 job 的组名
     */
    private static final String DEFAULT_JOB_GROUP = QuartzService.DEFAULT_JOB_GROUP;

    @Override
    public void addQuartzJob(CronJob job) {
        addQuartzJob(job, loadClass(job.getJobClass()));
    }

    private void addQuartzJob(CronJob job, Class<? extends Job> clazz) {
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(job.getJobName(), DEFAULT_JOB_GROUP)
                .usingJobData(QuartzService.DEFAULT_JOB_DATA_KEY, job.getId()).build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(), DEFAULT_JOB_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron())).build();
        try {
            if (JobStatusEnum.isDisabled(job.getJobStatus())) {
                pauseQuartzJob(job);
            } else {
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw new CronJobException(JobResultEnum.ADD_JOB_FAIL);
        }
    }

    @Override
    public void updateQuartzJob(CronJob job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), DEFAULT_JOB_GROUP);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
            if (JobStatusEnum.isDisabled(job.getJobStatus())) {
                pauseQuartzJob(job);
            }
        } catch (SchedulerException e) {
            throw new CronJobException(JobResultEnum.UPDATE_JOB_FAIL);
        }
    }

    @Override
    public void deleteQuartzJob(CronJob job) {
        Optional.of(JobKey.jobKey(job.getJobName(), DEFAULT_JOB_GROUP))
                .ifPresent(jobKey -> {
                    try {
                        scheduler.deleteJob(jobKey);
                    } catch (SchedulerException e) {
                        throw new CronJobException(JobResultEnum.DELETE_JOB_FAIL);
                    }
                });
    }

    @Override
    public void pauseQuartzJob(CronJob job) {
        Optional.of(JobKey.jobKey(job.getJobName(), DEFAULT_JOB_GROUP))
                .ifPresent(jobKey -> {
                    try {
                        scheduler.pauseJob(jobKey);
                    } catch (SchedulerException e) {
                        throw new CronJobException(JobResultEnum.PAUSE_JOB_FAIL);
                    }
                });
    }

    @Override
    public void resumeQuartzJob(CronJob job) {
        Optional.of(JobKey.jobKey(job.getJobName(), DEFAULT_JOB_GROUP))
                .ifPresent(jobKey -> {
                    try {
                        scheduler.resumeJob(jobKey);
                    } catch (SchedulerException e) {
                        throw new CronJobException(JobResultEnum.RESUME_JOB_FAIL);
                    }
                });
    }

    @Override
    public void immediateExecuteJob(CronJob job) {
        Optional.of(JobKey.jobKey(job.getJobName(), DEFAULT_JOB_GROUP))
                .ifPresent(jobKey -> {
                    try {
                        scheduler.triggerJob(jobKey);
                    } catch (SchedulerException e) {
                        throw new CronJobException(JobResultEnum.EXECUTE_JOB_FAIL);
                    }
                });
    }

    @Override
    public void updateQuartzJobExecuteClass(byte[] bytes, CronJob job) {
        try {
            deleteQuartzJob(job);
            addQuartzJob(job, (Class<? extends Job>) new QuartzClassLoader(bytes).loadClass(job.getJobClass()));
        } catch (ClassNotFoundException e) {
            throw new CronJobException("无法加载类");
        }
    }

    /**
     * 加载 class
     */
    private Class<? extends Job> loadClass(String className) {
        try {
            return (Class<? extends Job>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new CronJobException(String.format("[%s]对应的类找不到.", className));
        }
    }
}
