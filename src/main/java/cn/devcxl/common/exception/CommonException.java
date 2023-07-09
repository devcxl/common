package cn.devcxl.common.exception;

import cn.devcxl.common.exception.interfaces.ErrorCode;

/**
 * 通用异常
 * @author devcxl
 */
public class CommonException extends RuntimeException {

    private ErrorCode<?> errorCode;

    public CommonException(ErrorCode<?> errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode<?> getErrorCode() {
        return errorCode;
    }
}
