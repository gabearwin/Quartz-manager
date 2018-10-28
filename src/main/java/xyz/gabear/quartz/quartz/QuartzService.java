package xyz.gabear.quartz.quartz;

import xyz.gabear.quartz.domain.CronJob;

/**
 * quartz service
 */
public interface QuartzService {
    String DEFAULT_JOB_GROUP = "JOB_GROUP_NAME";
    String DEFAULT_JOB_DATA_KEY = "DATA_KEY";

    /**
     * 添加一个 quartz job
     */
    void addQuartzJob(CronJob job);

    /**
     * 更新 job
     */
    void updateQuartzJob(CronJob job);

    /**
     * 删除 job
     */
    void deleteQuartzJob(CronJob job);

    /**
     * 暂停一个 job
     */
    void pauseQuartzJob(CronJob job);

    /**
     * 恢复一个 job
     */
    void resumeQuartzJob(CronJob job);

    /**
     * 立即执行一次
     */
    void immediateExecuteJob(CronJob job);

    /**
     * 动态更新 job 执行的 class
     */
    void updateQuartzJobExecuteClass(byte[] bytes, CronJob cfg);
}
