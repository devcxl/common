package cn.devcxl.common.base;

import cn.devcxl.common.exception.interfaces.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 通用返回结构体
 *
 * @author devcxl
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResp<T> {

    /**
     * 返回Code
     */
    private Integer code;
    /**
     * 错误信息
     */
    private ErrorInfo error;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 返回消息
     */
    private String msg;

    public CommonResp(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public CommonResp(ErrorCode<?> errorCode, T data) {
        this.code = errorCode.getCode();
        this.data = data;
        this.msg = errorCode.getMessage();
    }

    public static <T> CommonResp<T> ok(T data) {
        return new CommonResp<T>(1000, data, "success");
    }

    public static <T> CommonResp<T> ok() {
        return new CommonResp<T>(1000, null, "success");
    }


    public static <T> CommonResp<T> fail(int code, String msg) {
        return new CommonResp<T>(code, null, msg);
    }

    public static <T> CommonResp<T> fail(ErrorCode<?> errorCode, String msg) {
        return new CommonResp<T>(errorCode.getCode(), null, msg);
    }

    public static <T> CommonResp<T> fail(ErrorCode<?> errorCode) {
        return new CommonResp<T>(errorCode.getCode(), null, errorCode.getMessage());
    }

    static class ErrorInfo {
        private Integer code;
        private String msg;
    }


}
