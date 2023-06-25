package cn.devcxl.common.exception;

import cn.devcxl.common.exception.base.GlobalException;
import cn.devcxl.common.exception.enums.CommonErrorCode;
import cn.devcxl.common.exception.interfaces.ErrorCode;

/**
 * 访问次数过多
 *
 * @author devcxl
 */
public class TooManyRequestsException extends GlobalException {

    public TooManyRequestsException() {
        super(CommonErrorCode.TOO_MANY_REQUESTS);
    }

    public TooManyRequestsException(ErrorCode<?> errorCode) {
        super(errorCode);
    }
}
