package cn.devcxl.common.exception;

import cn.devcxl.common.exception.base.GlobalException;
import cn.devcxl.common.exception.enums.CommonErrorCode;
import cn.devcxl.common.exception.interfaces.ErrorCode;

/**
 * 未认证错误
 *
 * @author devcxl
 */
public class UnauthorizedException extends GlobalException {

    public UnauthorizedException() {
        super(CommonErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(ErrorCode<?> errorCode) {
        super(errorCode);
    }
}
