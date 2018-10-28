package xyz.gabear.quartz.job.back;

import lombok.extern.slf4j.Slf4j;
import xyz.gabear.quartz.common.SpringApplicationContext;
import xyz.gabear.quartz.job.AbstractMutexJob;
import xyz.gabear.quartz.service.CronJobService;

import java.util.concurrent.TimeUnit;

/**
 * 测试互斥任务
 */
@Slf4j
public class DemoMutexJob002 extends AbstractMutexJob {

    @Override
    public void doExecute(String workDate) {
        log.info("测试demoMutexJob002开始执行...");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        SpringApplicationContext.getBean(CronJobService.class).queryAllEnableJobs().forEach(e -> log.warn(e.toString()));
        log.info("测试demoMutexJob002结束执行...");
    }
}
