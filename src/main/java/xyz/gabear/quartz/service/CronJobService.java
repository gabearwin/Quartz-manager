package xyz.gabear.quartz.service;

import xyz.gabear.quartz.domain.CronJob;

import java.util.List;

public interface CronJobService {

    /**
     * 添加一个任务
     */
    void addJob(CronJob cronJob);

    /**
     * 修改任务
     */
    void updateJob(CronJob cronJob);

    /**
     * 删除任务
     */
    void deleteJob(Long id);

    /**
     * 查询系统中所有状态为 启用 的任务
     */
    List<CronJob> queryAllEnableJobs();

    /**
     * 获取所有的任务配置
     */
    List<CronJob> findAll();

    /**
     * 根据主键获取一个任务
     */
    CronJob findById(Long id);

    /**
     * 动态更新job执行的class
     */
    void updateQuartzExecuteClass(byte[] bytes, Long id);
}
