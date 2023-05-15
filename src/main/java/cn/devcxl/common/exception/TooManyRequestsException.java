package cn.devcxl.common.exception;

import cn.devcxl.common.exception.base.GlobalException;
import cn.devcxl.common.exception.interfaces.ErrorCode;

/**
 * 访问次数过多
 *
 * @author devcxl
 */
public class TooManyRequestsException extends GlobalException {
    public TooManyRequestsException(ErrorCode<?> errorCode) {
        super(errorCode);
    }
}
