package cn.devcxl.common.exception.enums;

import cn.devcxl.common.exception.interfaces.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * @author devcxl
 */
public enum CommonErrorCode implements ErrorCode<CommonErrorCode> {


    /**
     * 错误的请求
     */
    BAD_REQUEST(1001, "BadRequest"),

    /**
     * 无权访问
     */
    FORBIDDEN(1002, "Forbidden"),

    /**
     * 没有找到
     */
    NOT_FOUND(1003, "NotFound"),

    /**
     * 请求超出
     */
    TOO_MANY_REQUESTS(1004, "TooManyRequests"),

    /**
     * 未认证
     */
    UNAUTHORIZED(1005, "Unauthorized");

    private int code;
    private String message;

    CommonErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {

        return this.message;
    }

}
