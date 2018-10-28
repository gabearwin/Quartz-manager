package xyz.gabear.quartz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.gabear.quartz.domain.CronJob;
import xyz.gabear.quartz.enums.JobStatusEnum;
import xyz.gabear.quartz.exception.CronJobException;
import xyz.gabear.quartz.quartz.QuartzService;
import xyz.gabear.quartz.repository.CronJobRepository;
import xyz.gabear.quartz.service.CronJobService;

import java.util.List;

@Service
public class CronJobServiceImpl implements CronJobService {

    @Autowired
    private CronJobRepository cronJobRepository;

    @Autowired
    private QuartzService quartzService;

    @Override
    @Transactional
    public void addJob(CronJob cronJob) {
        cronJobRepository.save(cronJob);
        quartzService.addQuartzJob(cronJob);
    }

    @Override
    @Transactional
    public void updateJob(CronJob cronJob) {
        cronJobRepository.save(cronJob);
        quartzService.updateQuartzJob(cronJob);
    }

    @Override
    @Transactional
    public void deleteJob(Long id) {
        CronJob cronJob = cronJobRepository.getOne(id);
        cronJobRepository.delete(cronJob);
        quartzService.deleteQuartzJob(cronJob);
    }

    @Override
    public List<CronJob> queryAllEnableJobs() {
        return cronJobRepository.findByJobStatus(JobStatusEnum.START.getCode());
    }

    @Override
    public List<CronJob> findAll() {
        return cronJobRepository.findAll();
    }

    @Override
    public CronJob findById(Long id) {
        return cronJobRepository.getOne(id);
    }

    @Override
    public void updateQuartzExecuteClass(byte[] bytes, Long id) {
        CronJob cronJob = findById(id);
        if (null == cronJob) {
            throw new CronJobException("[" + id + "]对应的job在数据库中不存在");
        }
        quartzService.updateQuartzJobExecuteClass(bytes, cronJob);
    }
}
