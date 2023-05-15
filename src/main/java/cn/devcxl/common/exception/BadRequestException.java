package cn.devcxl.common.exception;

import cn.devcxl.common.exception.base.GlobalException;
import cn.devcxl.common.exception.enums.CommonErrorCode;
import cn.devcxl.common.exception.interfaces.ErrorCode;

/**
 * 请求错误
 *
 * @author devcxl
 */
public class BadRequestException extends GlobalException {

    public BadRequestException() {
        super(CommonErrorCode.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(CommonErrorCode.BAD_REQUEST.setMessage(message));
    }

    public BadRequestException(ErrorCode<?> errorCode) {
        super(errorCode);
    }
}
