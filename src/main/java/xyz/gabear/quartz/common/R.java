package xyz.gabear.quartz.common;

import lombok.Data;

/**
 * vo
 */
@Data
public class R<T> {

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_FAIL = -1;

    private Integer code;
    private T data;
    private boolean success;

    public static <T> R<T> success(int code, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setSuccess(true);
        return r;
    }

    public static <T> R<T> success(T data) {
        return success(CODE_SUCCESS, data);
    }

    public static <T> R<T> fail(int code, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setSuccess(false);
        return r;
    }

    public static <T> R<T> fail(T data) {
        return success(CODE_FAIL, data);
    }

}
