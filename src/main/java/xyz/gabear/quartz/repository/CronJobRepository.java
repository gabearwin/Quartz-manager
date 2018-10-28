package xyz.gabear.quartz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.gabear.quartz.domain.CronJob;

import java.util.List;

public interface CronJobRepository extends JpaRepository<CronJob, Long> {
    /**
     * 根据任务状态获取任务
     */
    List<CronJob> findByJobStatus(String jobStatus);
}
