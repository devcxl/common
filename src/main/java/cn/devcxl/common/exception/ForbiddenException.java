package cn.devcxl.common.exception;

import cn.devcxl.common.exception.base.GlobalException;
import cn.devcxl.common.exception.enums.CommonErrorCode;
import cn.devcxl.common.exception.interfaces.ErrorCode;

/**
 * 无权访问
 *
 * @author devcxl
 */
public class ForbiddenException extends GlobalException {
    public ForbiddenException() {
        super(CommonErrorCode.FORBIDDEN);
    }

    public ForbiddenException(ErrorCode<?> errorCode) {
        super(errorCode);
    }
}
