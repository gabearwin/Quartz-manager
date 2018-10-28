package xyz.gabear.quartz.exception;

import xyz.gabear.quartz.enums.JobResultEnum;

/**
 * job 异常
 */
public class CronJobException extends RuntimeException {
    private Integer code;

    public CronJobException(JobResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CronJobException() {
        super();
    }

    public CronJobException(String message) {
        super(message);
    }

    public CronJobException(String message, Throwable cause) {
        super(message, cause);
    }


}
