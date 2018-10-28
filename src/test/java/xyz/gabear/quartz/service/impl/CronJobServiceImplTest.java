package xyz.gabear.quartz.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import xyz.gabear.quartz.QuartzApplicationTests;
import xyz.gabear.quartz.domain.CronJob;
import xyz.gabear.quartz.enums.JobStatusEnum;
import xyz.gabear.quartz.service.CronJobService;

import static org.junit.Assert.*;

@Slf4j
public class CronJobServiceImplTest extends QuartzApplicationTests {

    @Autowired
    private CronJobService cronJobService;

    @Test
    public void addJob() {
        CronJob cronJob = new CronJob();
        cronJob.setJobName("report");
        cronJob.setCron("0/10 * * * * ? ");
        cronJob.setJobClass("xyz.gabear.quartz.job.impl.DemoJob001");
        cronJob.setJobStatus(JobStatusEnum.START.getCode());
        cronJob.setComment("周报报表");
        cronJobService.addJob(cronJob);
    }

    @Test
    public void updateJob() {
        CronJob cronJob = new CronJob();
        cronJob.setId(3L);
        cronJob.setCron("0/10 * * * * ? ");
        cronJobService.updateJob(cronJob);
    }

    @Test
    public void deleteJob() {
    }

    @Test
    public void queryAllEnableJobs() {
    }

    @Test
    public void findAll() {
    }

    @Test
    @Transactional
    public void findById() {
        CronJob cronJob = cronJobService.findById(3L);
        log.info(cronJob.toString());
    }

    @Test
    public void updateQuartzExecuteClass() {
    }
}