package xyz.gabear.quartz.job.impl;

import lombok.extern.slf4j.Slf4j;
import xyz.gabear.quartz.common.SpringApplicationContext;
import xyz.gabear.quartz.job.AbstractCustomJob;
import xyz.gabear.quartz.service.CronJobService;

/**
 * 测试任务
 */
@Slf4j
public class DemoJob001 extends AbstractCustomJob {

    @Override
    public void doExecute(String jsonParameter) {
        log.info("测试demo001开始执行...");
        log.warn("接收到的参数是{}", jsonParameter);
        SpringApplicationContext.getBean(CronJobService.class).queryAllEnableJobs().forEach(e -> log.warn(e.toString()));
        log.info("测试demo001结束执行...");
    }
}
