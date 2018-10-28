package xyz.gabear.quartz.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.gabear.quartz.common.R;
import xyz.gabear.quartz.domain.CronJob;
import xyz.gabear.quartz.service.CronJobService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/job/manager")
@Api(tags = "Quartz job 配置管理控制层")
public class CronJobController {

    @Autowired
    private CronJobService cronJobService;

    @PostMapping("/dynamic/class")
    @ApiOperation("动态更新job执行的class文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long", paramType = "query")
    })
    public R<String> addOrUpdateCronJobAndJob(@ApiParam(value = "quartz job class file", required = true)
                                              @RequestParam("file") MultipartFile multipartFile, Long id) throws IOException {
        cronJobService.updateQuartzExecuteClass(multipartFile.getBytes(), id);
        return R.success("ok");
    }


    @PostMapping("/add")
    @ApiOperation("增加一个CronJob定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobCnName", value = "job的中文名字", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "jobName", value = "job的英文名字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "jobClass", value = "job任务执行的类", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cron", value = "job的调度表达式", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "parameter", value = "job执行额外参数，json格式", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "comment", value = "备注信息", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "successMail", value = "job执行成功发送邮件人", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "failMail", value = "job执行失败发送邮件人", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "jobStatus", value = "job的状态0-禁用 1-启用", required = true, dataType = "long", paramType = "query")
    })
    public R<String> addCronJob(CronJob cronJob) {
        cronJobService.addJob(cronJob);
        return R.success("ok");
    }

    @PostMapping("/update")
    @ApiOperation("根据ID修改CronJob定时任务")
    public R<String> updateCronJob(@RequestBody CronJob cronJob) {
        cronJobService.updateJob(cronJob);
        return R.success("ok");
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("根据id删除CronJob记录，并从系统中移除这个job")
    @ApiImplicitParam(name = "id", value = "任务主键", required = true, dataType = "long", paramType = "path")
    public R<String> deleteCronJob(@PathVariable("id") Long id) {
        cronJobService.deleteJob(id);
        return R.success("ok");
    }

    @GetMapping("/findAll")
    @ApiOperation("获取系统中所有的CronJob定时任务配置")
    public R<List<CronJob>> findAll() {
        return R.success(cronJobService.findAll());
    }
}
