package xyz.gabear.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.gabear.quartz.domain.CronJob;
import xyz.gabear.quartz.quartz.QuartzService;
import xyz.gabear.quartz.service.CronJobService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 互斥任务：
 * 上一次任务没有执行完，到了这次的执行时间，那么不进行这次任务的执行
 */
@Slf4j
public abstract class AbstractMutexJob implements Job {

    @Autowired
    private CronJobService cronJobService;

    private static final ConcurrentHashMap<String, AtomicBoolean> MUTEX_LOCK = new ConcurrentHashMap<String, AtomicBoolean>() {
        @Override
        public AtomicBoolean get(Object key) {
            return Optional.ofNullable(super.get(key)).orElse(new AtomicBoolean(false));
        }
    };

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Long cronId = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzService.DEFAULT_JOB_DATA_KEY);
        CronJob cronJob = cronJobService.findById(cronId);
        String jobName = cronJob.getJobName();
        if (MUTEX_LOCK.containsKey(jobName) && MUTEX_LOCK.get(jobName).get()) {
            log.info("任务:[{}]还在执行上一次任务,取消此次任务的执行.", jobName);
            return;
        }

        AtomicBoolean atomicBoolean = MUTEX_LOCK.get(jobName);
        boolean result = atomicBoolean.compareAndSet(false, true);

        if (!result) {
            log.info("并发执行此任务:[{}],但是被另外的一个线程抢先了.", jobName);
            return;
        }

        // 重新放回到 map 中
        MUTEX_LOCK.put(jobName, atomicBoolean);

        try {
            log.info("开始执行:[{}]", jobName);
            String workDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            doExecute(workDate);
        } catch (Exception e) {
            log.error(String.format("执行任务:[%s]失败", jobName), e);
        } finally {
            MUTEX_LOCK.get(jobName).compareAndSet(true, false);
        }
    }

    /**
     * 执行业务逻辑
     *
     * @param workDate 执行日期
     */
    public abstract void doExecute(String workDate);
}
