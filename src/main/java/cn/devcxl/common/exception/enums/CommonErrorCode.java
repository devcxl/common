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
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value() * 10, "BadRequest"),

    /**
     * 无权访问
     */
    FORBIDDEN(HttpStatus.FORBIDDEN.value() * 10, "Forbidden"),

    /**
     * 没有找到
     */
    NOT_FOUND(HttpStatus.NOT_FOUND.value() * 10, "NotFound"),

    /**
     * 请求超出
     */
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS.value() * 10, "TooManyRequests"),

    /**
     * 未认证
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value() * 10, "Unauthorized");

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
