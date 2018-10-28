package xyz.gabear.quartz.job.back;

import lombok.extern.slf4j.Slf4j;
import xyz.gabear.quartz.job.AbstractCustomJob;

/**
 * 测试动态的加载类
 */
@Slf4j
public class DynamicLoadJob extends AbstractCustomJob {

    @Override
    public void doExecute(String workDate) {
        log.info("==================================================================================");
        log.info("测试动态加载类 : [{}]", workDate);
        log.info("==================================================================================");
    }
}
