package xyz.gabear.quartz.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.gabear.quartz.service.CronJobService;

/**
 * quartz job 初始化
 */
@Component
@Slf4j
public class QuartzJobInit implements InitializingBean {

    @Autowired
    private CronJobService cronJobService;

    @Autowired
    private QuartzService quartzService;

    @Override
    public void afterPropertiesSet() {
        log.info("开始--初始化数据库中所有定时任务");
        // 将数据库中配置的所有任务进行调度
        cronJobService.findAll().forEach(job -> quartzService.addQuartzJob(job));
        log.info("结束--初始化数据库中所有定时任务");
    }

}
