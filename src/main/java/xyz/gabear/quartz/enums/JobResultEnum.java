package xyz.gabear.quartz.enums;

import lombok.Getter;

@Getter
public enum JobResultEnum {
    ADD_JOB_FAIL(1, "添加 quartz job 失败"),
    UPDATE_JOB_FAIL(2, "更新 job 失败"),
    DELETE_JOB_FAIL(3, "删除 job 失败"),
    PAUSE_JOB_FAIL(4, "暂停 job 失败"),
    RESUME_JOB_FAIL(5, "恢复 job 失败"),
    EXECUTE_JOB_FAIL(6, "执行 job 失败"),
    ;
    private Integer code;

    private String message;

    JobResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
