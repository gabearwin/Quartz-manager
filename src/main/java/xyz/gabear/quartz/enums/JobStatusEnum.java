package xyz.gabear.quartz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * job 的状态
 */
@AllArgsConstructor
@Getter
public enum JobStatusEnum {

    START("1", "启用"),

    DISABLED("0", "禁用");

    private String code;
    private String desc;

    public static JobStatusEnum from(String jobCode) {
        return Arrays.stream(JobStatusEnum.values())
                .filter(job -> Objects.equals(job.getCode(), jobCode))
                .findFirst().orElse(JobStatusEnum.DISABLED);
    }

    /**
     * job 是否启用
     */
    public static boolean isStart(String jobCode) {
        return Objects.equals(START, from(jobCode));
    }

    /**
     * job 是否禁用
     */
    public static boolean isDisabled(String jobCode) {
        return Objects.equals(DISABLED, from(jobCode));
    }

}
