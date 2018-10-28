package xyz.gabear.quartz.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 使 普通的 java 类中可以使用 spring 的依赖注入功能
 */
@Component
@Slf4j
public class QuartzJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected Job adaptJob(Object job) throws Exception {
        log.info("实例化了Class:[{}]的对象.", job.getClass());
        autowireCapableBeanFactory.autowireBean(job);
        return super.adaptJob(job);
    }
}
